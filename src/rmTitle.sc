;;; Sierra Script 1.0 - (do not remove this comment)
(script# RM_TITLE)
(include sci.sh)
(include Verbs.sh)
(include 0.shm)
(include game.sh)

(use Main)
(use System)

(use Controls)
(use Print)
(use Cycle)
(use Game)
(use Actor)

(public
	rm9001 0
)

(instance myDialog of Dialog
	(properties)
)

(instance rm9001 of Room
	(properties
		picture scriptNumber
		style (| dpANIMATION_BLACKOUT dpOPEN_FADEPALETTE)
	)
	
	(method (init)
		; Set port to the entire screen, since our title image is 200px high.
		(SetPort 0 0 190 320 10 0)
		(if gDialog (gDialog dispose:))
		(super init:)
		(gOldMH addToFront: self)
		(gOldKH addToFront: self)
		(gGame setCursor: 996 1)
		(gIconBar hide: disable:)
		(gUser canInput: TRUE)
		(self setScript: roomScript)

	)
	
	(method (dispose)
		; Restore the port to standard size.
		(SetPort 0 0 190 320 10 0)
		(gIconBar hide: enable:)
		(= gNormalCursor 999)
		(gGame setCursor: 996 1)
		(gOldKH delete: self)
		(gOldMH delete: self)
		(super dispose: &rest)
	)
)

(instance roomScript of Script
	(properties)
	
	(method (changeState newState &tmp temp0)
		(switch (= state newState)
			(0
				(gMusic1 number: SONG_TITLE_SCREEN loop: 1 play: self)
				(= seconds 3)
			)
			(1
				(romanFour init: setCycle: Forward)
				(romanFour setCycle: EndLoop)
				(= seconds 5)
			)
			(2
				(= seconds 0)
				(= gNormalCursor 999)
				(gGame setCursor: 999 1)
				(restoreButton init:)
				(openingButton init:)
				(helpButton init:)
				(playButton init:)
			)
		)
	)

	(method (handleEvent pEvent)
		(if (not (pEvent claimed?))
			(delegateEvent pEvent restoreButton)
		)
		(if (not (pEvent claimed?))
			(delegateEvent pEvent openingButton)
		)
		(if (not (pEvent claimed?))
			(delegateEvent pEvent helpButton)
		)
		(if (not (pEvent claimed?))
			(delegateEvent pEvent playButton)
		else
			(super handleEvent: pEvent)
		)
	)
)

(instance romanFour of Prop
	(properties
		x 168
		y 120
		view VIEW_ROMAN_NUMERAL_4
        cycleSpeed 10
	)
)

(instance restoreButton of Actor
	(properties
		x 51
		y 175
		view VIEW_TITLE_BUTTONS
		loop LOOP_RESTORE_BUTTON
		cel CEL_ON_HOVER_BUTTON
	)
	
	(method (handleEvent pEvent)
		(DebugPrint {restoreButton handleEvent:})
	)
)

(instance openingButton of Prop
	(properties
		x 106
		y 175
		view VIEW_TITLE_BUTTONS
		loop LOOP_OPENING_BUTTON
		cel CEL_ON_HOVER_BUTTON
	)
	
	(method (handleEvent pEvent)
		(DebugPrint {openingButton handleEvent:})
	)
)

(instance helpButton of Prop
	(properties
		x 159
		y 175
		view VIEW_TITLE_BUTTONS
		loop LOOP_HELP_BUTTON
		cel CEL_ON_HOVER_BUTTON
	)
	
	(method (handleEvent pEvent)
		(DebugPrint {helpButton handleEvent:})
	)
)

(instance playButton of Prop
	(properties
		x 212
		y 174
		view VIEW_TITLE_BUTTONS
		loop LOOP_PLAY_BUTTON
		cel CEL_ON_HOVER_BUTTON
	)
	
	(method (handleEvent pEvent)
		(if (& (pEvent type?) (| evVERB evMOUSERELEASE))
			(gRoom newRoom: RM_OGRE_LIVING_ROOM)
		)
	)
)

(procedure (delegateEvent pEvent prop &tmp toggleCel)
	(if
		(and
			(& (pEvent type?) (| evVERB (| evMOUSERELEASE evMOUSEBUTTON)))
			(<= (prop nsLeft?) (pEvent x?))
			(<= (pEvent x?) (prop nsRight?))
			(<= (prop nsTop?) (pEvent y?))
			(<= (pEvent y?) (prop nsBottom?))
		)
		(prop setCel: (^ (prop cel?) 1))
		(prop handleEvent: pEvent)
		(pEvent claimed: 1)
	)
)