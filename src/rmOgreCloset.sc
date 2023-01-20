;;; Sierra Script 1.0 - (do not remove this comment)
(script# RM_OGRE_CLOSET)
(include sci.sh)
(include Verbs.sh)
(include game.sh)
(include 1704.shm)
(include 1704.shp)

(use Main)
(use System)

(use Controls)
(use Print)
(use Cycle)
(use Game)
(use Actor)
(use Game)

(public
	rm1704 0
)

(instance rm1704 of Room
	(properties
		picture scriptNumber
		horizon 50
		vanishingX 130
		vanishingY 50
		noun N_ROOM
	)
	
	(method (init)
		(super init:)
		(switch gPreviousRoomNumber
			; Add room numbers here to set up the ego when coming from different directions.
			(else 
				(SetUpEgo -1 1)
				(gEgo posn: 150 130)
			)
		)
		(gEgo init:)
		; We just came from the title screen, so we need to call this to give control
		; to the player.
		(gGame handsOn:)
	)
)
