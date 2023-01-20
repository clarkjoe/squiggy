;;; Sierra Script 1.0 - (do not remove this comment)
(script# RM_LOGO)
(include sci.sh)
(include Verbs.sh)
(include 9000.shm)
(include game.sh)

(use Main)
(use System)

(use Controls)
(use Print)
(use Cycle)
(use Game)
(use Actor)

(public
	rm9000 0
)

(instance myDialog of Dialog
	(properties)
)

(instance rm9000 of Room
	(properties
		picture scriptNumber
		style (| dpANIMATION_BLACKOUT dpOPEN_FADEPALETTE)
	)

	(method (init)
		; Set port to the entire screen, since our title image is 200px high.
		(SetPort 0 0 190 320 0 0)
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

	(method (changeState newState)
		(switch (= state newState)
			(0
				(= seconds 6)
			)
			(1
				(gRoom newRoom: RM_TITLE)
			)
		)
	)
)