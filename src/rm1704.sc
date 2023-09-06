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

(public
	rm1704 0
)

(instance rm1704 of Room
	(properties
		picture scriptNumber
		noun N_ROOM
	)
	
	(method (init)
		(gRoom addObstacle: (&getpoly {}))
		(super init:)
		(self setScript: RoomScript)
		(switch gPreviousRoomNumber
			(CABIN_ENTRANCE_SCRIPT
				(gEgo posn: 167 145 loop: STILL_LOOP cel: STILL_DOWN_CEL)
			)
			(else 
				; Set up ego view and loop (direction)
				(SetUpEgo -1 0)
				(gEgo posn: 167 145)
			)
		)
		(gEgo init:)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)
		; code executed each game cycle
		(= egoOnControl (gEgo onControl:))
		
		(if (& ctlLIME egoOnControl) (gRoom newRoom: CABIN_ENTRANCE_SCRIPT))
	)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0 ; Handle state changes
			)
		)
	)
)
