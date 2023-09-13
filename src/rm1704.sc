;;; Sierra Script 1.0 - (do not remove this comment)
(script# CABIN_CLOSET_SCRIPT)
(include sci.sh)
(include game.sh)
(include 1704.shm)
(use Actor)
(use Cycle)
(use Door)
(use Feature)
(use Game)
(use Main)
(use Polygon)
(use System)
(use Scaler)

(public
	rm1704 0
)

(instance rm1704 of Room
	(properties
		picture scriptNumber
		noun N_ROOM
	)
	
	(method (init)
		(gRoom addObstacle: (&getpoly {contained}))
		(super init:)
		(self setScript: entrance)
		(switch gPreviousRoomNumber
			(else
				(SetUpEgo -1 0)
				(gEgo posn: 167 145 loop: STILL_LOOP cel: STILL_DOWN_CEL)
			)
		)
		(gEgo init: setScale: Scaler 100 100 0 1)
		(keyhole init:)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)

		(= egoOnControl (gEgo onControl:))
		
		(if (& ctlLIME egoOnControl) (gRoom newRoom: CABIN_ENTRANCE_SCRIPT))
	)
	
	(method (changeState newState)
		(= state newState)
		(= gCabinKeyholeState newState)
		(switch gCabinKeyholeState
			(0
				(cond
					((== gKeyholePic CABIN_KEYHOLE_PIC_SLEEP)
						(self changeState: 3)
					)
					(else
						(self cue:)
					)
				)
			)
			(1
				(= seconds 5)
			)
			(2
				(= gKeyholePic CABIN_KEYHOLE_PIC_OGRE)
				(gMessager say: N_ROOM 0 C_OGRE_ENTERS 0)
			)
			(3
			)
		)
	)
)

(instance entrance of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)

		(= egoOnControl (gEgo onControl:))
		
		(if (& ctlLIME egoOnControl) (gRoom newRoom: CABIN_ENTRANCE_SCRIPT))
	)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(= seconds 5)
			)
			(1
				(rm1704 setScript: RoomScript)
			)
		)
	)
)

(instance keyhole of Feature
	(properties
		noun N_KEYHOLE
		approachX 200
		approachY 134
	)

	(method (init)
		(self 
			setOnMeCheck: omcCOLORS ctlNAVY
			approachVerbs: V_LOOK
		)
		(super init: &rest)
	)	
	
	
	(method (doVerb theVerb)
		(switch theVerb
			(V_LOOK
				(gRoom newRoom: CABIN_KEYHOLE_SCRIPT)
			)
			(else 
				(super doVerb: theVerb &rest)
			)
		)
	)
)