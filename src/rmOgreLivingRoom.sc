;;; Sierra Script 1.0 - (do not remove this comment)
(script# RM_OGRE_LIVING_ROOM)
(include sci.sh)
(include Verbs.sh)
(include game.sh)
(include 1701.shm)
(include 1701.shp)

(use Main)
(use System)

(use Controls)
(use Print)
(use Cycle)
(use Game)
(use Actor)
(use Game)

(public
	rm1701 0
)

(instance rm1701 of Room
	(properties
		picture scriptNumber
		horizon 50
		vanishingX 130
		vanishingY 50
		noun N_ROOM
	)
	
	(method (init)
		(super init:)
		(AddPolygonsToRoom @P_Default1701)
		(switch gPreviousRoomNumber
			(RM_OGRE_UPSTAIRS
				(SetUpEgo -1 1)
				(gEgo posn: 76 91 loop: 1 cel: 0)
			)
			(RM_OGRE_KITCHEN
				(SetUpEgo -1 1)
				(gEgo posn: 40 184 loop: 0 cel: 0)
			)
			(else 
				(SetUpEgo -1 1)
				(gEgo posn: 245 136 loop: 2 cel: 0)
			)
		)
		(gEgo init:)
		(gGame handsOn:)
	)

	(method (doit)
		(if (& (gEgo onControl:) ctlGREEN)
			(gRoom newRoom: RM_OGRE_UPSTAIRS)
		)
		(if (& (gEgo onControl:) ctlTEAL)
			(gRoom newRoom: RM_OGRE_KITCHEN)
		)
		(super doit:)
	)
)

