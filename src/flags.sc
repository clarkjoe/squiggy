;;; Sierra Script 1.0 - (do not remove this comment)
(script# 985)
(include sci.sh)
(use System)


;	
;	 Provides a way to compactly store a bunch of boolean flags, in the same
;	 manner as :func:`Btest`, :func:`Bset` and :func:`Bclear`.
(class Flags of Object
	(properties
		size 0
		array 0
	)
	
	(method (init)
		(if (and size (not array)) (self setSize: size))
	)
	
	(method (dispose)
		(if array (Memory memFREE array) (= array 0))
		(super dispose:)
	)
	
	; Sets the maximum number of flags.
	(method (setSize param1 &tmp temp0 temp1)
		(= temp0 (/ param1 16))
		(if (mod param1 16) (++ temp0))
		(= size (* temp0 16))
		(= array (Memory memALLOC_CRIT (* temp0 2)))
		(= temp1 0)
		(while (< temp1 temp0)
			(Memory memPOKE (+ array (* temp1 2)) 0)
			(++ temp1)
		)
	)
	
	; Sets a flag (or multiple flags).
	(method (set theFlag &tmp temp0 temp1)
		(while argc
			(= temp1 (* (/ [theFlag (-- argc)] 16) 2))
			(= temp0 (Memory memPEEK (+ array temp1)))
			(= temp0 (| temp0 (>> $8000 (mod [theFlag argc] 16))))
			(Memory memPOKE (+ array temp1) temp0)
		)
	)
	
	; Clears a flag (or multiple flags).
	(method (clear theFlag &tmp temp0 temp1)
		(while argc
			(= temp1 (* (/ [theFlag (-- argc)] 16) 2))
			(= temp0 (Memory memPEEK (+ array temp1)))
			(= temp0
				(& temp0 (~ (>> $8000 (mod [theFlag argc] 16))))
			)
			(Memory memPOKE (+ array temp1) temp0)
		)
	)
	
	; Returns TRUE if the flag is set.
	(method (test theFlag &tmp temp0 temp1)
		(= temp1 (* (/ theFlag 16) 2))
		(= temp0 (Memory memPEEK (+ array temp1)))
		(return (& temp0 (>> $8000 (mod theFlag 16))))
	)
)
