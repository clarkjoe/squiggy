;;; Sierra Script 1.0 - (do not remove this comment)
(script# CABIN_ENTRANCE_SCRIPT)
(include sci.sh)
(include game.sh)
(include 1701.shm)
(use Actor)
(use Cycle)
(use Door)
(use Feature)
(use Game)
(use Main)
(use Polygon)
(use System)
(use Scaler)
(use StopWalk)
(use PolyPath)

(public
	rm1701 0
)

(instance rm1701 of Room
	(properties
		picture scriptNumber
		noun N_ROOM
	)

	(method (init)
		(gRoom addObstacle: (&getpoly {contained}))
		(gRoom addObstacle: (&getpoly {globe}))
		(super init:)
		(SetUpEgo -1 0)
		(switch gPreviousRoomNumber
			(CABIN_KITCHEN_SCRIPT
				(self setScript: RoomScript)
				(gEgo posn: 259 130 loop: STILL_LOOP cel: STILL_LEFT_CEL)
			)
			(CABIN_BEDROOM_SCRIPT
				(self setScript: enterFromBedroom)
			)
			(CABIN_CLOSET_SCRIPT
				(self setScript: RoomScript)
				(gEgo posn: 204 121 loop: STILL_LOOP cel: STILL_DOWN_CEL)
			)
			(else
				(self setScript: RoomScript)
				(gEgo posn: 44 145)
			)
		)
		(gEgo init: setScale: Scaler 85 85 150 120)
		
		(if (== gKeyholePic CABIN_KEYHOLE_PIC_SLEEP)
			(ogre init: setMotion: Forward setSpeed: 60)
		)

		(gGame handsOn:)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)
		(= egoOnControl (gEgo onControl:))
		
		(if (& ctlLIME egoOnControl) (gRoom newRoom: CABIN_CLOSET_SCRIPT))
		(if (& ctlCYAN egoOnControl) (rm1701 setScript: exitToBedroom))
		(if (& ctlFUCHSIA egoOnControl) (gRoom newRoom: CABIN_KITCHEN_SCRIPT))
	)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0 ; Handle state changes
			)
		)
	)
)

(instance enterFromBedroom of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(gGame handsOff:)
				(gEgo init: setScale: Scaler 75 75 0 1)
				(gEgo posn: 218 43)
				(gEgo setMotion: PolyPath 183 43 self)
			)
			(1
				(gGame handsOn:)
				(rm1701 setScript: RoomScript)
			)
		)
	)
)

(instance exitToBedroom of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(gGame handsOff:)
				(gEgo setMotion: PolyPath 222 43 self)
			)
			(1
				(gRoom newRoom: CABIN_BEDROOM_SCRIPT)
			)
		)
	)
)

(instance ogre of Actor
	(properties
		view OGRE_SLEEPING_VIEW
		x 242
		y 170
		signal ignAct
		noun N_OGRE
	)
)
