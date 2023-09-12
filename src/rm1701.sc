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
(use Wander)

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
		(self setScript: RoomScript)
		(SetUpEgo -1 0)
		(switch gPreviousRoomNumber
			(CABIN_KITCHEN_SCRIPT
				(gEgo posn: 259 130 loop: STILL_LOOP cel: STILL_LEFT_CEL)
			)
			(CABIN_BEDROOM_SCRIPT
				(gEgo posn: 188 42 loop: STILL_LOOP cel: STILL_LEFT_CEL)
			)
			(CABIN_CLOSET_SCRIPT
				(gEgo posn: 204 143 loop: STILL_LOOP cel: STILL_DOWN_CEL)
			)
			(else
				(gEgo posn: 44 145)
			)
		)
		(gEgo
			init:
			setScale: Scaler 75 75 150 120
		)

		(gGame handsOn:)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)
		; code executed each game cycle
		(= egoOnControl (gEgo onControl:))
		
		(if (& ctlLIME egoOnControl) (gRoom newRoom: CABIN_CLOSET_SCRIPT))
		(if (& ctlCYAN egoOnControl) (gRoom newRoom: CABIN_BEDROOM_SCRIPT))
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