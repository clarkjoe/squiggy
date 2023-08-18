;;; Sierra Script 1.0 - (do not remove this comment)
(script# 1704)
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
		north 0
		east 0
		south 0
		west 0
		noun N_ROOM
	)
	
	(method (init)
		; Uncomment this line once one or more polygons have been defined in the picture editor.
		; (gRoom addObstacle: (&getpoly ""))
		(super init:)
		(self setScript: RoomScript)
		(switch gPreviousRoomNumber
			(else 
				; Set up ego view and loop (direction)
				(SetUpEgo -1 0)
				(gEgo posn: 150 100)
			)
		)
		(gEgo init:)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit)
		(super doit:)
		; code executed each game cycle
	)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0 ; Handle state changes
			)
		)
	)
)
