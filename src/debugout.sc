;;; Sierra Script 1.0 - (do not remove this comment)
; This script contains the DebugPrint console functionality that works with SCI Companion.
(script# DEBUGOUT_SCRIPT)
(include sci.sh)
(include game.sh)
(use Main)
(use Controls)
(use System)
(use User)
(use File)
(use Print)
(use Game)
(use DebugRoomInit)

(public



	debugOut 0
)


(instance debugOut of File
	(properties
		; ndebug.log is a file that exists as open and unwriteable while the game is running.
		; This is how we detect when the game exits.
		name {ndebug.log}
	)
	
	(method (init initDebugFilename &tmp tempHandle [numberBuffer 10] startRoom tempRoom heapPointer)
		(= startRoom -1)
		; Open initDebugFilename to see if it has a script number in it. If so, we'll start in that room.
		(= tempHandle
			(FileIO fiOPEN initDebugFilename fOPENFAIL)
		)
		(if tempHandle
			(if
				(!=
					(FileIO fiREAD_STRING @numberBuffer 10 tempHandle)
					-1
				)
				(= tempRoom (ReadNumber @numberBuffer))
				(if tempRoom
					; Check to see if export #0 of this script id
					(= heapPointer (ScriptID tempRoom 0))
					(if (IsObject heapPointer)
						(if (heapPointer isKindOf: Room)
							(= startRoom tempRoom)
							; Set up any initialization required if we're going right to that room:
							(DebugRoomInit startRoom)
							; Unload the DebugRoomInit script right after, as we don't need it anymore.
							(DisposeScript DEBUGROOM_SCRIPT)
						)
						; REVIEW: We only want to do this if it wasn't loaded before...
						(DisposeScript tempRoom)
					)
				)
			)
			(FileIO fiCLOSE tempHandle)
		)
		; Open ourselves for write
		(self open: fOPENCREATE)
		(return startRoom)
	)
	
	(method (debugPrint &tmp tempHandle temp0 temp1)
		; We open and close the file for each write, since that appears to be the only way to flush.
		(= tempHandle (FileIO fiOPEN {debug.log} fOPENCREATE))
		(if tempHandle
			(= temp0 (FindFormatLen &rest))
			(= temp1 (Memory memALLOC_CRIT temp0))
			(Format temp1 &rest)
			(FileIO fiWRITE_STRING tempHandle temp1)
			(FileIO fiWRITE_STRING tempHandle {\n})
			(Memory memFREE temp1)
			(FileIO fiCLOSE tempHandle)
		)
	)
)
