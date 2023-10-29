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
(use DPath)

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
;;;		(self setScript: ogressPaces)
		(SetUpEgo -1 0)
		(switch gPreviousRoomNumber
			(else
				(gEgo posn: 67 154 setHeading: 90)
			)
		)
		(gEgo init: setScale: Scaler 75 75 0 1)
		(ogress init:)
		(knife init: hide:)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)
		
		(= egoOnControl (gEgo onControl:))
		(if (& ctlLIME egoOnControl) (gRoom newRoom: CABIN_ENTRANCE_SCRIPT))
	)
)

(instance ogressPaces of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)
		
		(= egoOnControl (gEgo onControl:))
		
		(if (and
				(or
					(== (self state:) 4)
					(== (self state:) 8)
				)
				(== (& ctlNAVY egoOnControl) FALSE)
			)
			
			(ogress setScript: ogressGrabsRosella self)
			(self dispose:)
		)
	)
	
	(method (changeState newState)
		(= state newState)
		(switchto state
			(
				(ogress view: OGRESS_COOK_VIEW setLoop: OGRESS_KNEAD_LOOP setCycle: Forward)
				(= seconds 3)
			)
			(
				(ogress setLoop: OGRESS_CHOP_LOOP setCycle: EndLoop self)
			)
			(
				(= seconds 1)
			)
			(
				(knife show:)
				(ogress view: OGRESS_VIEW setCycle: StopWalk -1 setLoop: LEFT_LOOP)
				(self cue:)
			)
			(
				(ogress setMotion: PolyPath 159 144 self)
			)
			(
				(ogress setMotion: PolyPath 159 124 self)
			)
			(
				(ogress view: OGRESS_COOK_VIEW setLoop: OGRESS_STIR_LOOP setCycle: Forward)
				(= seconds 5)
			)
			(
				(ogress view: OGRESS_VIEW setCycle: StopWalk -1 setLoop: DOWN_LOOP)
				(self cue:)
			)
			(
				(ogress setMotion: PolyPath 159 144 self)
			)
			(
				(ogress setMotion: PolyPath 197 144 self)
			)
			(
				(knife hide:)
				(self cue:)
			)
			(
				(self changeState: 0)
			)
		)
	)
)

(instance ogressGrabsRosella of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)
	)
	
	(method (changeState newState)
		(= state newState)
		(switchto state
			(
				(gMessager say: N_ROOM 0 C_OGRESS_SEE 0)
				(self cue:)
			)
			(
				(ogress setSpeed: 4)
				(ogress setMotion: PChase gEgo 30 self)
			)
			(
				(gGame handsOff:)
				(ogress setSpeed: 7)
				(self cue:)
			)
			(
				(gEgo dispose:)
				(ogress view: OGRESS_CATCH_ROSELLA_VIEW)
				(if (< (gEgo x?) (ogress x?))
					(ogress setLoop: 0)
				else
					(ogress setLoop: 1)
				)
				
				(ogress x: (gEgo x?) y: (gEgo y?) setCel: 0 setCycle: EndLoop self)
			)
			(
				(ogress view: OGRESS_CARRY_ROSELLA_VIEW setCycle: Walk setMotion: PolyPath 197 153 self)
			)
			(
				(ogress view: OGRESS_PREP_PIE_A_VIEW setCycle: EndLoop self)
			)
			(
				(ogress view: OGRESS_PREP_PIE_B_VIEW setCycle: EndLoop self)
			)
			(
				(ogress view: OGRESS_CARRY_PIE_VIEW setCycle: Walk setMotion: PolyPath 159 124 self)
			)
			(
				(ogress view: OGRESS_COOK_PIE_VIEW setCycle: EndLoop self)
			)
			(
				(DebugPrint {DEATH})
			)
		)
	)
)

(instance ogress of Actor
	(properties
		view OGRESS_VIEW
		x 197
		y 144
		signal ignAct
		noun N_OGRESS
	)
	
	(method (init)
		(super init: &rest)
		(self 
			setStep: 8 4
			setSpeed: 7
			setScale: Scaler 80 80 0 1
			setScript: ogressPaces
		)
	)
)

(instance knife of Prop
	(properties
		view OGRESS_KNIFE_VIEW
		x 221
		y 88
		signal ignAct
		noun N_KNIFE
	)
)
