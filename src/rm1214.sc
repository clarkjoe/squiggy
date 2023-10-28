;;; Sierra Script 1.0 - (do not remove this comment)
(script# CABIN_FRONT_SCRIPT)
(include sci.sh)
(include game.sh)
(include 1214.shm)
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
	rm1214 0
)

(instance rm1214 of Room
	(properties
		picture scriptNumber
		north 0
		east 0
		south 0
		west 0
		noun N_ROOM
	)
	
	(method (init)
		(gRoom addObstacle: (&getpoly {}))
		(super init:)
		(SetUpEgo -1 0)
		(self setScript: RoomScript)
		(switch gPreviousRoomNumber
			(else
				(SetUpEgo -1 0)
				(gEgo posn: 100 181)
			)
		)
		(gEgo init: setScale: Scaler 50 50 150 120)
		(bone init: approachVerbs: V_DO setScale: Scaler 50 50 150 120)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)
		(= egoOnControl (gEgo onControl:))
		
		(cond
			((& ctlNAVY egoOnControl)
				(gRoom newRoom: CABIN_ENTRANCE_SCRIPT)
			)
		)
	)
)

(instance getBone of Script
	(properties)
	
	(method (changeState newState &tmp newSpeed)
		(= state newState)
		(switchto state
			(
				(gGame handsOff:)
				(self cue:)
			)
			(
				(= newSpeed gGEgoMoveSpeed)
				(gEgo view: ROSELLA_PEASANT_PICKUP_VIEW setCel: 0 setSpeed: (+ gGEgoMoveSpeed 2))
				(if (<= (gEgo x?) (bone x?))
					(gEgo setLoop: 0)
				else
					(gEgo setLoop: 2)
				)
				(self cue:)
			)
			(
				(gEgo setCycle: EndLoop self)
			)
			(
				(bone dispose:)
				(gEgo get: INV_BONE setCel: 0 setLoop: (+ (gEgo loop?) 1))
				(self cue:)
			)
			(
				(gEgo setCycle: EndLoop self)
			)
			(
				(gEgo view: ROSELLA_PEASANT_VIEW setSpeed: (- gGEgoMoveSpeed 2))
				(self cue:)
			)
			(
				(SetUpEgo -1 2)
				(gEgo init:)
				(self cue:)
			)
			(
				(gGame handsOn:)
				(rm1214 setScript: RoomScript)
			)
		)
	)
)

(instance bone of Actor
	(properties
		view BONE_VIEW
		loop INVENTORY_ITEM_GAME_LOOP
		noun N_BONE
		x 91
		y 177
		approachX 86
		approachY 178
		signal ignAct
		priority -1
	)
	
	(method (doVerb theVerb)
		(switch theVerb
			(V_DO
				(rm1214 setScript: getBone)
			)
			(else 
				(super doVerb: theVerb &rest)
			)
		)
	)
)