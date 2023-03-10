;;; Sierra Script 1.0 - (do not remove this comment)
(script# MOVECYCLE_SCRIPT)
(include sci.sh)
(include game.sh)
(use Main)
(use Cycle)
(use System)


;	
;	 A cycler that progresses through an explicit set of cels. See the init() method for more information about parameters.
;	
;	 Example usage::
;	
;	 	; Defined at the top of the script:
;	 	(local [celList 5] = [3 6 2 1 $8000]) // $8000 indicates the end of the cel list.
;	
;	 	; Then in code:
;	 	(aStar setCycle: MoveCycle celList) // cycle through 3, 6, 2, 1 then stop.
;	
(class MoveCycle of Cycle
	(properties
		name {MCyc}
		client 0
		caller 0
		cycleDir 1
		cycleCnt 0
		completed 0
		value 0
		points 0
		size 0
	)
	
	;
	; .. function:: init(theClient thePoints [theCaller cycleDir])
	;
	; 	:param heapPtr theClient: The object to which the cycler is attached.
	; 	:param heapPtr thePoints: An array of cels.
	; 	:param heapPtr theCaller: An optional object that will be cued when we're done.
	; 	:param number cycleDir: 1 to go forward, or -1 to go backward.
	;
	(method (init theClient thePoints theCaller theCycleDir &tmp [temp0 2])
		(= client theClient)
		(= points thePoints)
		(if (>= argc 3)
			(cond 
				((>= argc 4) (= cycleDir theCycleDir) (= caller theCaller))
				((IsObject theCaller) (= caller theCaller))
				(else (= cycleDir theCaller))
			)
		)
		(= size 0)
		(while (!= (WordAt points size) -32768)
			(++ size)
		)
		(if (== cycleDir 1)
			(= value 0)
		else
			(= value (- size 4))
		)
		(super init:)
	)
	
	(method (doit)
		(if
		(>= (Abs (- gGameTime cycleCnt)) (client cycleSpeed?))
			(= cycleCnt gGameTime)
			(self nextCel:)
		)
	)
	
	(method (nextCel)
		(client
			loop: (WordAt points value)
			cel: (WordAt points (+ value 1))
			x: (WordAt points (+ value 2))
			y: (WordAt points (+ value 3))
		)
		(= value (+ value (* cycleDir 4)))
		(if
			(or
				(and (== cycleDir 1) (>= value size))
				(and (== cycleDir -1) (< value 0))
			)
			(self cycleDone:)
		)
	)
	
	(method (cycleDone)
		(= completed 1)
		(= value 0)
		(if caller (= gDoMotionCue TRUE) else (self motionCue:))
	)
)
