;;; Sierra Script 1.0 - (do not remove this comment)
;	
;	 This script contains the important Messager class. This manages automatically showing sequences of messages,
;	 including invoking the appropriate :class:`Talker` instances to display the messages.
(script# 924)
(include sci.sh)
(use Main)
(use Print)
(use Game)
(use System)


;	
;	 Messager is responsible for automatically showing sequences of messages,
;	 including invoking the appropriate :class:`Talker` instances to display the messages.
;	
;	 In general, only one instance of Messager exists in the game, gMessager in the main.sc script.
;	
;	 Messager is mostly commonly triggered automatically by the player performing an action on an object (e.g. a verb on a noun).
;	 However, sometimes you will want to invoke it directly if you have more complex logic to deal with certain situations.
;	 The principal method of interest for callers is say().
;	
;	 Example usage::
;	
;	 	(gMessager say: noun verb condition seqeuence caller roomNumber)	
;	
(class Messager of Object
	(properties
		caller 0
		disposeWhenDone 1
		oneOnly 0
		killed 0
		oldIconBarState 0
		curSequence 0
		lastSequence 0
		talker 0
	)
	
	(method (dispose)
		(talkerSet dispose:)
		(if gIconBar
			(gIconBar state: oldIconBarState)
			(= oldIconBarState 0)
		)
		(if caller
			(if (not gCuees) (= gCuees (Set new:)))
			(gCuees
				add: ((Cue new:)
					cuee: caller
					cuer: self
					register: killed
					yourself:
				)
			)
		)
		(= talker 0)
		(super dispose:)
	)
	
	; Cues the Messager, which triggers the next message in a sequence to be displayed.
	(method (cue param1)
		(if (and argc param1) (= killed 1))
		(if (or oneOnly killed)
			(if gFastCast
				(gFastCast release: dispose:)
				(= gFastCast 0)
			)
			(self dispose:)
		else
			(self sayNext:)
		)
	)
	
	;
	; .. function:: say(noun [verb cond seq caller modnum])
	;
	; .. function:: say(noun [verb cond seq lastSeq caller modnum])
	;
	; 	Causes the specified message to be shown using its associated :class:`Narrator` or :class:`Talker`. If seq is 0,
	; 	then all messages in the sequence are shown. Otherwise, only the one with the specified sequence is shown.
	;
	; 	:param number noun: The noun of the message to show.
	; 	:param number verb: The verb of the message to show.
	; 	:param number cond: The condition of the message to show.
	; 	:param number seq: The sequence number of the message to show, or 0 to show all messages in a sequence.
	; 	:param number lastSeq: The last sequence to show.
	; 	:param object caller: Optional caller.
	; 	:param number modNum: Optional room number of the message to show (if not specified, the current room is used).
	;
	; .. function:: say(-1 [caller])
	;
	; 	This version of say does the same thing as the parameterless sayNext(), but lets you specify a new caller.
	;
	;
	(method (say params &tmp noun verb case modNum [temp4 20] temp24)
		(= case (= curSequence 0))
		(= verb case)
		(= noun verb)
		(= caller (= oneOnly (= killed 0)))
		(if (and gIconBar (not oldIconBarState))
			(= oldIconBarState (gIconBar state?))
		)
		(= noun [params 0])
		(if (== noun -1)
			(if (and (> argc 1) (IsObject [params 1]))
				(= caller [params 1])
			)
			(self sayNext:)
		else
			(if (and (> argc 1) [params 1]) (= verb [params 1]))
			(if (and (> argc 2) [params 2]) (= case [params 2]))
			(if (and (> argc 3) [params 3])
				(= oneOnly 1)
				(= curSequence [params 3])
			else
				(= curSequence 1)
			)
			(= temp24 4)
			(if
				(and
					(> argc temp24)
					[params
					temp24]
					(not (IsObject [params temp24]))
				)
				(= lastSequence [params temp24])
				(++ temp24)
				(= oneOnly 0)
			else
				(= lastSequence 0)
			)
			(if (and (> argc temp24) [params temp24])
				(= caller [params temp24])
			else
				(= caller 0)
			)
			(= modNum
				(if (> argc (++ temp24))
					[params
					temp24]
				else
					gRoomNumber
				)
			)
			(if
				(and
					gMessageType
					(Message msgGET modNum noun verb case curSequence)
				)
				(self sayNext: modNum noun verb case curSequence)
			else
				(Print
					addTextF:
						{<Messager>\n\tmsgType set to 0 or\n\t%d: %d, %d, %d, %d not found}
						modNum
						noun
						verb
						case
						curSequence
					init:
				)
				(self dispose:)
			)
		)
	)
	
	; Directly invokes the :class:`Narrator` or :class:`Talker` with the formatted message string.
	(method (sayFormat talkerNumber formatString theCaller &tmp temp0 buffer theTalker)
		(if (and gIconBar (not oldIconBarState))
			(= oldIconBarState (gIconBar state?))
		)
		(= theTalker (self findTalker: talkerNumber))
		(= temp0 (FindFormatLen formatString theCaller &rest))
		(if (IsObject [theCaller (- argc 2)])
			(= caller [theCaller (- argc 2)])
		)
		(= oneOnly 1)
		(= buffer (Memory memALLOC_CRIT temp0))
		(Format buffer formatString theCaller &rest)
		(theTalker say: buffer self)
		(Memory memFREE buffer)
	)
	
	;
	; .. function:: sayNext()
	;
	; .. function:: sayNext(theModNum noun verb cond seq)
	;
	; 	This displays the next message in a sequence. It should generally only be called by the framework.
	;
	(method (sayNext theModNum noun verb case seq &tmp theTalker [buffer 200] temp201)
		(if argc
			(= theTalker
				(Message msgGET theModNum noun verb case seq @buffer)
			)
		else
			(= theTalker (Message msgNEXT @buffer))
		)
		(if (& gMessageType $0002)
			(= temp201 (Memory memALLOC_CRIT 12))
			(Message msgLAST_MESSAGE temp201)
		)
		(if
			(and
				theTalker
				(or
					(not lastSequence)
					(and lastSequence (<= curSequence lastSequence))
				)
			)
			(= theTalker (self findTalker: theTalker))
			(if
				(and
					talker
					(!= theTalker talker)
					(== (talker disposeWhenDone?) 2)
				)
				(talker caller: 0 dispose: 1)
			)
			(= talker theTalker)
			(if (!= theTalker -1)
				(talkerSet add: theTalker)
				(if (& gMessageType $0002)
					(theTalker modNum: theModNum say: temp201 self)
				else
					(theTalker
						modNum: theModNum
						say: @buffer self theModNum noun verb case seq
					)
				)
				(++ curSequence)
			else
				(if gFastCast
					(gFastCast release: dispose:)
					(= gFastCast 0)
				)
				(self dispose:)
			)
		else
			(if gFastCast
				(gFastCast release: dispose:)
				(= gFastCast 0)
			)
			(self dispose:)
		)
		(if (& gMessageType $0002) (Memory memFREE temp201))
	)
	
	;
	; .. function:: findTalker(talkerNumber)
	;
	; 	This message should be overridden in subclasses to return a talker object based upon a talker number.
	;
	(method (findTalker)
		(Print
			width: 200
			addText:
				{<Messager findTalker:>\n\tCan't find talker or\n\tfindTalker method not over-ridden}
			init:
		)
		(return gNarrator)
	)
)

(instance talkerSet of Set
	(properties)
	
	(method (dispose)
		(self
			eachElementDo: #caller 0
			eachElementDo: #dispose (gMessager disposeWhenDone?)
			release:
		)
		(super dispose:)
	)
)
