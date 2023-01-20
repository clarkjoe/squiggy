;;; Sierra Script 1.0 - (do not remove this comment)
(script# RM_OGRE_KITCHEN)
(include sci.sh)
(include Verbs.sh)
(include game.sh)
(include 1702.shm)
(include 1702.shp)

(use Main)
(use System)

(use Controls)
(use Print)
(use Cycle)
(use Game)
(use Actor)
(use Game)

(public
	rm1702 0
)

(instance rm1702 of Room
	(properties
		picture scriptNumber
		horizon 50
		vanishingX 130
		vanishingY 50
		noun N_ROOM
	)
	
	(method (init)
		(super init:)
		(AddPolygonsToRoom @P_Default1702)
		(switch gPreviousRoomNumber
			(else 
				(SetUpEgo -1 1)
				(gEgo posn: 136 180 loop: 3 cel: 0)
			)
		)
		(gEgo init:)
	)

	(method (doit)
		(if (& (gEgo onControl:) ctlNAVY)
			(gRoom newRoom: RM_OGRE_LIVING_ROOM)
		)
		
		(super doit:)
	)
)
