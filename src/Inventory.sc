;;; Sierra Script 1.0 - (do not remove this comment)
;	
;	 This is the script that you modify to add additional inventory item instances that inherit from :class:`InventoryItem`.
;	
;	 An example might be::
;	
;	 	(instance Hammer of InventoryItem
;	 		(properties
;	 			view 900
;	 			loop 1
;	 			cursor 900			; Optional cursor when using this item.
;	 			message V_HAMMER	; Optional verb associated with the item.
;	 			signal $0002
;	 			noun N_HAMMER		; noun from message resource 0
;	 		)
;	 	)
;	
;	 Then in templateInventory::init(), add the inventory item to the add: call.
;	
(script# INVENTORY_SCRIPT)
(include sci.sh)
(include game.sh)
(include 15.shm)
(use Main)
(use ScrollableInventory)
(use ScrollInsetWindow)
(use Print)
(use IconItem)
(use InventoryItem)
(use System)

(public
	invCode 0
	invWin 1
)

(instance templateInventory of ScrollableInventory
	(properties)
	
	(method (init)
		(super init: &rest)
		(invWin
			color: gColorWindowForeground
			back: 3
			topBordColor: 5
			lftBordColor: 4
			rgtBordColor: 2
			botBordColor: 1
			insideColor: 2
			topBordColor2: 1
			lftBordColor2: 1
			botBordColor2: 6
			rgtBordColor2: 6
		)
		(self
			; Add inventory items here.
			add: hen
			add: bone
			add: axe
			eachElementDo: #lowlightColor 2
			add: invLook invSelect invHelp invUp invDown ok
		)
		(self
			state: 2048
			upIcon: invUp
			downIcon: invDown
			window: invWin
			helpIconItem: invHelp
			selectIcon: invSelect
			okButton: ok
			numCols: 5
			scrollAmount: 5
			dispAmount: 10
			empty: 13
			normalHeading: 15
			eachElementDo: #highlightColor 0
			eachElementDo: #modNum INVENTORY_SCRIPT
			eachElementDo: #init
		)
	)
)

(instance invCode of Code
	(properties)
	
	(method (init)
		(= gInv templateInventory)
		(gInv init:)
	)
)

(instance invWin of ScrollInsetWindow
	(properties
		priority -1
		topBordHgt 30
		botBordHgt 5
	)
	
	(method (open)
		(invLook
			nsLeft: (- (/ (- (self right?) (self left?)) 2) 84)
		)
		(invLook nsTop: 2)
		(super open: &rest)
	)
)

(instance invUp of IconItem
	(properties
		view 991
		loop 5
		cel 0
		cursor 999
		maskView 991
		maskLoop 5
		maskCel 2
		lowlightColor 5
		noun N_UPICON
		helpVerb V_HELP
	)
	
	(method (select)
		(if (super select: &rest) (gInv scroll: -1))
		(return 0)
	)
)

(instance invDown of IconItem
	(properties
		view 991
		loop 6
		cel 0
		cursor 999
		maskView 991
		maskLoop 6
		maskCel 2
		lowlightColor 5
		noun N_DOWNICON
		helpVerb V_HELP
	)
	
	(method (select)
		(if (super select: &rest) (gInv scroll: 1))
		(return 0)
	)
)

(instance ok of IconItem
	(properties
		view 991
		loop 3
		cel 0
		cursor 999
		signal $0043
		lowlightColor 5
		noun N_CLOSEBUTTON
		helpVerb V_HELP
	)
)

(instance invLook of IconItem
	(properties
		view 991
		loop 2
		cel 0
		cursor 981
		message V_LOOK
		signal $0081
		lowlightColor 5
		noun N_DESCICON
		helpVerb V_HELP
	)
)

(instance invHand of IconItem
	(properties
		view 991
		loop 0
		cel 0
		cursor 982
		message V_DO
		lowlightColor 5
		noun N_DOICON
		helpVerb V_HELP
	)
)

(instance invHelp of IconItem
	(properties
		view 991
		loop 1
		cel 0
		cursor 989
		message V_HELP
		lowlightColor 5
		noun N_HELPICON
		helpVerb V_HELP
	)
)

(instance invSelect of IconItem
	(properties
		view 991
		loop 4
		cel 0
		cursor 999
		lowlightColor 5
		noun N_SELECTICON
		helpVerb V_HELP
	)
)

(instance hen of InventoryItem
    (properties
        view HEN_VIEW
        cursor HEN_VIEW
        loop INVENTORY_ITEM_PURSE_LOOP
        signal $0002
        noun N_HEN
    )
)

(instance bone of InventoryItem
    (properties
        view BONE_VIEW
        cursor BONE_VIEW
        loop INVENTORY_ITEM_PURSE_LOOP
        signal $0002
        noun N_BONE
        message V_BONE
    )
)

(instance axe of InventoryItem
    (properties
        view AXE_VIEW
        cursor AXE_VIEW
		loop INVENTORY_ITEM_PURSE_LOOP
        signal $0002
        noun N_AXE
    )
)
