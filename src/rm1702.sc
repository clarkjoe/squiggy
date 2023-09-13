;;; Sierra Script 1.0 - (do not remove this comment)
(script# CABIN_KITCHEN_SCRIPT)
(include sci.sh)
(include game.sh)
(include 1702.shm)
(use Actor)
(use Cycle)
(use Door)
(use Feature)
(use Game)
(use Main)
(use Polygon)
(use System)
(use Scaler)
(use ScaleTo)
(use StopWalk)
(use PolyPath)
(use PChase)

(public
	rm1702 0
)

(instance rm1702 of Room
	(properties
		picture scriptNumber
		noun N_ROOM
	)
	
	(method (init)
		(gRoom addObstacle: (&getpoly {contained}))
		(super init:)
		(self setScript: RoomScript)
		(SetUpEgo -1 0)
		(switch gPreviousRoomNumber
			(else
				(gEgo posn: 67 154 setHeading: 90)
			)
		)
		(gEgo init: setScale: Scaler 100 100 0 1)
		(ogress init: setScale: Scaler 225 225 0 1 setCycle: StopWalk -1)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)
		
		(= egoOnControl (gEgo onControl:))
		
		(if (and
				(or
					(== (self state:) 1)
					(== (self state:) 3)
				)
				(== (& ctlNAVY egoOnControl) FALSE)
			)
			
			(rm1702 setScript: ogressGrabsRosella)
		)
		
		(if (& ctlLIME egoOnControl) (gRoom newRoom: CABIN_ENTRANCE_SCRIPT))
	)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(= seconds 6)
			)
			(1
				(ogress setMotion: PolyPath 159 124 self)
			)
			(2
				(= seconds 3)
			)
			(3
				(ogress setMotion: PolyPath 185 152 self)
			)
			(4
				(ogress setHeading: 90)
				(self cue:)
			)
			(5
				(self changeState: 0)
			)
		)
	)
)

(instance ogressGrabsRosella of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(gGame handsOff:)
				(self cue:)
			)
			(1
				(ogress setMotion: PChase gEgo 50 self)
			)
			(2
				(DebugPrint {DEATH})
			)
		)
	)
)

(instance ogress of Actor
	(properties
		view GENESTA_VIEW
		x 185
		y 152
		signal ignAct
		noun N_OGRESS
	)
)