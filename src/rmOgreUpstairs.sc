;;; Sierra Script 1.0 - (do not remove this comment)
(script# RM_OGRE_UPSTAIRS)
(include sci.sh)
(include Verbs.sh)
(include game.sh)
(include 1703.shm)
(include 1703.shp)

(use Main)
(use System)

(use Controls)
(use Print)
(use Cycle)
(use Game)
(use Actor)
(use Game)

(public
	rm1703 0
)

(instance rm1703 of Room
	(properties
		picture scriptNumber
		horizon 50
		vanishingX 130
		vanishingY 50
		noun N_ROOM
	)
	
	(method (init)
		(super init:)
		(AddPolygonsToRoom @P_Default1703)
		(switch gPreviousRoomNumber
			(else 
				(SetUpEgo -1 1)
				(gEgo posn: 45 185 loop: 0 cel: 0)
			)
		)
		(gEgo init:)
		(if (not (gEgo has: INV_AXE))
		    (axe approachVerbs: V_DO init:)
		)
	)

	(method (doit)
		(if (& (gEgo onControl:) ctlGREEN)
			(gRoom newRoom: RM_OGRE_LIVING_ROOM)
		)
		(super doit:)
	)
)

(instance axe of Prop
	(properties
		view VIEW_AXE
		x 216
		y 152
		signal ignAct
		loop 2
		cel 0
		noun N_AXE
		priority -1
	)

	(method (doVerb theVerb)
		(switch theVerb
			(V_DO
				(gEgo get: INV_AXE)
                (axe hide:)
			)
			(else 
				(super doVerb: theVerb &rest)
			)
		)
	)
)
