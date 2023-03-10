;;; Sierra Script 1.0 - (do not remove this comment)
(script# 968)
(include sci.sh)
(use Cycle)
(use System)

(public
	SmoothLooper 0
)

;	
;	 SmoothLooper is a looper (a class that determines the view loop based on Actor direction)
;	 that lets you specify a different view that is used when changing directions.
;	 This allows for smooth turns when an :class:`Actor` heads in a new direction.
;	
;	 Example usage::
;	
;	 	(instance mySmooper of SmoothLooper
;	 		(properties
;	 			cycleSpeed 6
;	 			vChangeDir 3931
;	 		)
;	 	)
;	
;	 	// Then in code:
;	 	(theGuy looper: mySmooper)
;	
(class SmoothLooper of Code
	(properties
		nextLoop 0
		client 0
		oldCycler 0
		oldMover 0
		newMover 0
		oldCycleSpeed 0
		cycleSpeed 0
		inProgress 0
		vNormal 0
		vChangeDir 0
	)
	
	(method (doit theClient theAngle &tmp clientLoop temp1)
		(if (& (theClient signal?) $0800) (return))
		(= temp1 0)
		(if inProgress
			(if newMover (newMover dispose:))
			(= newMover (theClient mover?))
			(theClient mover: 0)
			(return)
		else
			(if (not vNormal) (= vNormal (theClient view?)))
			(= client theClient)
			(= inProgress 1)
		)
		(if
		(and (> (client loop?) 3) (== (client view?) vNormal))
			(if inProgress
				(if (IsObject oldMover) (oldMover dispose:))
			else
				(client view: vNormal)
				(DirLoop client theAngle)
			)
		)
		(switch (= clientLoop (client loop?))
			(3
				(cond 
					((or (<= theAngle 45) (> theAngle 315)))
					((<= theAngle 135) (= clientLoop 4) (= nextLoop 0) (= temp1 1))
					((<= theAngle 225) (= clientLoop 4) (= nextLoop 16) (= temp1 1))
					((<= theAngle 315) (= clientLoop 5) (= temp1 (= nextLoop 1)))
				)
			)
			(0
				(cond 
					((or (<= theAngle 45) (> theAngle 315)) (= clientLoop 6) (= nextLoop 3) (= temp1 1))
					((<= theAngle 135))
					((<= theAngle 225) (= clientLoop 0) (= nextLoop 2) (= temp1 1))
					((<= theAngle 315) (= clientLoop 6) (= nextLoop 21) (= temp1 1))
				)
			)
			(1
				(cond 
					((or (<= theAngle 45) (> theAngle 315)) (= clientLoop 7) (= nextLoop 3) (= temp1 1))
					((<= theAngle 135) (= clientLoop 1) (= nextLoop 18) (= temp1 1))
					((<= theAngle 225) (= clientLoop 1) (= nextLoop 2) (= temp1 1))
					(else (<= theAngle 315))
				)
			)
			(2
				(cond 
					((or (<= theAngle 45) (> theAngle 315)) (= clientLoop 3) (= nextLoop 23) (= temp1 1))
					((<= theAngle 135) (= clientLoop 2) (= nextLoop 0) (= temp1 1))
					((<= theAngle 225))
					((<= theAngle 315) (= clientLoop 3) (= temp1 (= nextLoop 1)))
				)
			)
		)
		(if temp1
			(= oldCycler (client cycler?))
			(= oldMover (client mover?))
			(= oldCycleSpeed (client cycleSpeed?))
			(client
				view: vChangeDir
				cycleSpeed: cycleSpeed
				mover: 0
				cycler: 0
				loop: clientLoop
				cel: 0
				setCycle: EndLoop self
			)
		else
			(= inProgress 0)
		)
	)
	
	(method (dispose)
		(if oldMover (oldMover dispose:))
		(if newMover (newMover dispose:))
		(if oldCycler (oldCycler dispose:))
		(= inProgress
			(= oldMover (= newMover (= oldCycler 0)))
		)
		(client view: vNormal looper: 0)
		(DirLoop client (client heading?))
		(super dispose:)
	)
	
	(method (cue &tmp [temp0 2])
		(if (< nextLoop 15)
			(client
				view: vNormal
				loop: nextLoop
				mover: oldMover
				cycler: oldCycler
				cycleSpeed: oldCycleSpeed
			)
			(= inProgress (= oldCycler (= oldMover 0)))
			(if newMover
				(client setMotion: newMover)
				(= newMover 0)
			)
		else
			(= nextLoop (- nextLoop 16))
			(client loop: nextLoop cel: 0 setCycle: EndLoop self)
			(= nextLoop
				(switch nextLoop
					(0 2)
					(5 1)
					(2 0)
					(7 3)
				)
			)
		)
	)
)
