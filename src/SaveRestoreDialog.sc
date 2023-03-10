;;; Sierra Script 1.0 - (do not remove this comment)
; Contains the code for saving and restoring games. This should all be handled automatically for you.
(script# SAVERESTORE_SCRIPT)
(include sci.sh)
(use Main)
(use Controls)
(use Print)
(use DialogControls)
(use File)

(public
	GetDirectory 0
)

(local
	local0
	local1
	local2
	local3
	local4
	[local5 15]
	[local20 15]
	[local35 15]
	[local50 15]
	[local65 25]
)
(procedure (GetDirectory param1 &tmp temp0 [temp1 33] [temp34 100] [temp134 50])
	(asm
code_0949:
		pushi    #font
		pushi    1
		pushi    0
		pushi    205
		pushi    7
		pushi    1
		pushi    0
		pushi    0
		pushi    1
		pushi    0
		pushi    0
		pushi    990
		pushi    207
		pushi    5
		pushi    2
		lea      @temp1
		push    
		lsp      param1
		callk    StrCpy,  4
		push    
		pushi    29
		pushi    0
		pushi    20
		lsp      param1
		pushi    212
		pushi    8
		pushi    1
		pushi    27
		pushi    0
		pushi    0
		pushi    1
		pushi    0
		pushi    34
		pushi    990
		pushi    212
		pushi    8
		pushi    0
		pushi    38
		pushi    0
		pushi    0
		pushi    1
		pushi    50
		pushi    34
		pushi    990
		pushi    110
		pushi    0
		class    Print
		send     82
		sat      temp0
		not     
		bnt      code_09a4
		ldi      0
		ret     
code_09a4:
		pushi    1
		lea      @temp1
		push    
		callk    StrLen,  2
		not     
		bnt      code_09b7
		pushi    1
		lea      @temp1
		push    
		callk    GetCWD,  2
code_09b7:
		pushi    1
		lea      @temp1
		push    
		callk    ValidPath,  2
		bnt      code_09d1
		pushi    2
		lsp      param1
		lea      @temp1
		push    
		callk    StrCpy,  4
		ldi      1
		ret     
		jmp      code_0949
code_09d1:
		pushi    7
		pushi    0
		pushi    990
		pushi    29
		pushi    0
		pushi    0
		pushi    1
		lea      @temp134
		push    
		callk    Message,  14
		pushi    3
		lea      @temp34
		push    
		lea      @temp134
		push    
		lea      @temp1
		push    
		callk    Format,  6
		pushi    #font
		pushi    1
		pushi    0
		pushi    205
		pushi    1
		lea      @temp34
		push    
		pushi    110
		pushi    0
		class    Print
		send     16
		jmp      code_0949
		ret     
	)
)

(procedure (GetStatus)
	(return
		(cond 
			((== self Restore) 0)
			((HaveSpace) 1)
			(local2 2)
			(else 3)
		)
	)
)

(procedure (HaveSpace)
	(if (< local2 20) (CheckFreeSpace gSaveDir))
)

(procedure (NeedDescription)
	(Print font: 0 addText: 3 0 0 1 0 0 990 init:)
)

;	
;	 The save/restore dialog base class.
(class SaveRestoreDialog of Dialog
	(properties
		name {SRDialog}
		elements 0
		size 0
		text 0
		font 0
		window 0
		theItem 0
		nsTop 0
		nsLeft 0
		nsBottom 0
		nsRight 0
		time 0
		caller 0
		seconds 0
		lastSeconds 0
		eatTheMice 0
		lastTicks 0
	)
	
	(method (init param1 param2 param3 &tmp [temp0 250])
		(= window gWindow)
		(= nsBottom 0)
		(= local2 (GetSaveFiles (gGame name?) param2 param3))
		(if (== local2 -1) (return 0))
		(= local4 (GetStatus))
		(if (== local4 1)
			(editI
				text: (StrCpy param1 param2)
				font: gSmallFont
				setSize:
				moveTo: 4 4
			)
			(self add: editI setSize:)
		)
		(selectorI
			text: param2
			font: gSmallFont
			setSize:
			moveTo: 4 (+ nsBottom 4)
			state: 2
		)
		(switch local4
			(0
				(Message msgGET 990 26 0 0 1 @local5)
			)
			(1
				(Message msgGET 990 28 0 0 1 @local5)
			)
			(else 
				(Message msgGET 990 25 0 0 1 @local5)
			)
		)
		(= local1 (+ (selectorI nsRight?) 4))
		(okI
			text: @local5
			setSize:
			moveTo: local1 (selectorI nsTop?)
			state:
				(if
				(or (and (== local4 0) (not local2)) (== local4 3))
					0
				else
					3
				)
		)
		(Message msgGET 990 24 0 0 1 @local20)
		(deleteI
			text: @local20
			setSize:
			moveTo: local1 (+ (okI nsBottom?) 4)
			state: (if (not local2) 0 else 3)
		)
		(Message msgGET 990 23 0 0 1 @local35)
		(changeDirI
			text: @local35
			setSize:
			moveTo: local1 (+ (deleteI nsBottom?) 4)
			state: (& (changeDirI state?) (~ csSELECTED))
		)
		(Message msgGET 990 22 0 0 1 @local50)
		(cancelI
			text: @local50
			setSize:
			moveTo: local1 (+ (changeDirI nsBottom?) 4)
			state: (& (cancelI state?) (~ csSELECTED))
		)
		(self
			add: selectorI okI deleteI changeDirI cancelI
			setSize:
		)
		(switch local4
			(0
				(Message msgGET 990 10 0 0 1 @temp0)
			)
			(1
				(Message msgGET 990 11 0 0 1 @temp0)
			)
			(else 
				(Message msgGET 990 30 0 0 1 @temp0)
			)
		)
		(textI
			text: @temp0
			setSize: (- (- nsRight nsLeft) 8)
			moveTo: 4 4
		)
		(= local1 (+ (textI nsBottom?) 4))
		(self eachElementDo: #move 0 local1)
		(self add: textI setSize: center: open: 4 -1)
		(return 1)
	)
	
	(method (doit param1 &tmp temp0 temp1 temp2 [temp3 361] [temp364 21] [temp385 140])
		(asm
			pushSelf
			lofsa    Restore
			eq?     
			bnt      code_03ec
			lap      argc
			bnt      code_03ec
			lap      param1
			bnt      code_03ec
			pushi    2
			pushi    fiOPEN
			pushi    3
			lea      @temp385
			push    
			lofsa    {%ssg.dir}
			push    
			pushi    #name
			pushi    0
			lag      gGame
			send     4
			push    
			callk    Format,  6
			push    
			callk    FileIO,  4
			sat      temp0
			push    
			ldi      65535
			eq?     
			bnt      code_03e5
			ret     
code_03e5:
			pushi    2
			pushi    fiCLOSE
			lst      temp0
			callk    FileIO,  4
code_03ec:
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			not     
			bnt      code_0404
			ldi      65535
			ret     
code_0404:
			lsl      local4
			dup     
			ldi      0
			eq?     
			bnt      code_041a
			lal      local2
			bnt      code_0415
			lofsa    okI
			jmp      code_0433
code_0415:
			lofsa    changeDirI
			jmp      code_0433
code_041a:
			dup     
			ldi      1
			eq?     
			bnt      code_0425
			lofsa    editI
			jmp      code_0433
code_0425:
			dup     
			ldi      2
			eq?     
			bnt      code_0430
			lofsa    okI
			jmp      code_0433
code_0430:
			lofsa    changeDirI
code_0433:
			toss    
			sal      local0
			pushi    #doit
			pushi    1
			push    
			super    Dialog,  6
			sal      local1
			pushi    #indexOf
			pushi    1
			pushi    #cursor
			pushi    0
			lofsa    selectorI
			send     4
			push    
			lofsa    selectorI
			send     6
			sal      local3
			push    
			ldi      18
			mul     
			sat      temp2
			lsl      local1
			lofsa    changeDirI
			eq?     
			bnt      code_04aa
			pushi    #dispose
			pushi    0
			self     4
			pushi    1
			lsg      gSaveDir
			call     GetDirectory,  2
			bnt      code_0495
			pushi    3
			pushi    #name
			pushi    0
			lag      gGame
			send     4
			push    
			lea      @temp3
			push    
			lea      @temp364
			push    
			callk    GetSaveFiles,  6
			sal      local2
			push    
			ldi      65535
			eq?     
			bnt      code_0495
			ldi      65535
			sat      temp1
			jmp      code_072e
code_0495:
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			jmp      code_0404
code_04aa:
			lsl      local4
			ldi      2
			eq?     
			bnt      code_04f4
			lsl      local1
			lofsa    okI
			eq?     
			bnt      code_04f4
			pushi    #dispose
			pushi    0
			self     4
			pushi    #doit
			pushi    1
			pushi    2
			lsp      param1
			lat      temp2
			leai     @temp3
			push    
			callk    StrCpy,  4
			push    
			lofsa    GetReplaceName
			send     6
			bnt      code_04df
			lal      local3
			lati     temp364
			sat      temp1
			jmp      code_072e
code_04df:
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			jmp      code_0404
code_04f4:
			lsl      local4
			ldi      1
			eq?     
			bnt      code_05b5
			lsl      local1
			lofsa    okI
			eq?     
			bt       code_050d
			lsl      local1
			lofsa    editI
			eq?     
			bnt      code_05b5
code_050d:
			pushi    1
			lsp      param1
			callk    StrLen,  2
			push    
			ldi      0
			eq?     
			bnt      code_0538
			pushi    #dispose
			pushi    0
			self     4
			pushi    0
			call     NeedDescription,  0
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			jmp      code_0404
code_0538:
			ldi      65535
			sat      temp1
			ldi      0
			sal      local1
code_0540:
			lsl      local1
			lal      local2
			lt?     
			bnt      code_055f
			pushi    2
			lsp      param1
			lsl      local1
			ldi      18
			mul     
			leai     @temp3
			push    
			callk    StrCmp,  4
			sat      temp1
			not     
			bt       code_055f
			+al      local1
			jmp      code_0540
code_055f:
			lat      temp1
			not     
			bnt      code_056e
			lal      local1
			lati     temp364
			sat      temp1
			jmp      code_072e
code_056e:
			lsl      local2
			ldi      20
			eq?     
			bnt      code_057f
			lal      local3
			lati     temp364
			sat      temp1
			jmp      code_072e
code_057f:
			ldi      0
			sat      temp1
code_0583:
			ldi      1
			bnt      code_072e
			ldi      0
			sal      local1
code_058c:
			lsl      local1
			lal      local2
			lt?     
			bnt      code_05a1
			lst      temp1
			lal      local1
			lati     temp364
			eq?     
			bt       code_05a1
			+al      local1
			jmp      code_058c
code_05a1:
			lsl      local1
			lal      local2
			eq?     
			bnt      code_05ab
			jmp      code_072e
code_05ab:
			+at      temp1
			jmp      code_0583
			jmp      code_072e
			jmp      code_0404
code_05b5:
			lsl      local1
			lofsa    deleteI
			eq?     
			bnt      code_06da
			pushi    #dispose
			pushi    0
			self     4
			pushi    #addText
			pushi    7
			pushi    12
			pushi    0
			pushi    0
			pushi    1
			pushi    0
			pushi    0
			pushi    990
			pushi    212
			pushi    8
			pushi    0
			pushi    31
			pushi    0
			pushi    0
			pushi    1
			pushi    0
			pushi    35
			pushi    990
			pushi    212
			pushi    8
			pushi    1
			pushi    32
			pushi    0
			pushi    0
			pushi    1
			pushi    50
			pushi    35
			pushi    990
			pushi    110
			pushi    0
			class    Print
			send     62
			not     
			bnt      code_0614
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			jmp      code_0404
code_0614:
			pushi    #name
			pushi    1
			pushi    3
			pushi    7
			lea      @temp385
			push    
			pushi    #name
			pushi    0
			lag      gGame
			send     4
			push    
			callk    DeviceInfo,  6
			push    
			pushi    196
			pushi    1
			pushi    2
			pushi    #new
			pushi    0
			class    File
			send     4
			sat      temp0
			send     12
			ldi      2570
			sat      temp1
			ldi      0
			sal      local1
code_0646:
			lsl      local1
			lal      local2
			lt?     
			bnt      code_0687
			lsl      local1
			lal      local3
			ne?     
			bnt      code_0683
			pushi    #write
			pushi    2
			lal      local1
			leai     @temp364
			push    
			pushi    2
			lat      temp0
			send     8
			pushi    338
			pushi    #x
			lsl      local1
			ldi      18
			mul     
			leai     @temp3
			push    
			lat      temp0
			send     6
			pushi    #write
			pushi    2
			lea      @temp1
			push    
			pushi    1
			lat      temp0
			send     8
code_0683:
			+al      local1
			jmp      code_0646
code_0687:
			ldi      65535
			sat      temp1
			pushi    #write
			pushi    2
			lea      @temp1
			push    
			pushi    2
			pushi    342
			pushi    0
			pushi    111
			pushi    0
			lat      temp0
			send     16
			pushi    4
			pushi    8
			lea      @temp385
			push    
			pushi    #name
			pushi    0
			lag      gGame
			send     4
			push    
			lal      local3
			lsti     temp364
			callk    DeviceInfo,  8
			pushi    2
			pushi    fiUNLINK
			lea      @temp385
			push    
			callk    FileIO,  4
			pushi    #init
			pushi    3
			lsp      param1
			lea      @temp3
			push    
			lea      @temp364
			push    
			self     10
			jmp      code_0404
code_06da:
			lsl      local1
			lofsa    okI
			eq?     
			bnt      code_06ee
			lal      local3
			lati     temp364
			sat      temp1
			jmp      code_072e
			jmp      code_0404
code_06ee:
			lsl      local1
			ldi      65535
			eq?     
			bt       code_06fd
			lsl      local1
			lofsa    cancelI
			eq?     
			bnt      code_0706
code_06fd:
			ldi      65535
			sat      temp1
			jmp      code_072e
			jmp      code_0404
code_0706:
			lsl      local4
			ldi      1
			eq?     
			bnt      code_0404
			pushi    #cursor
			pushi    1
			pushi    1
			pushi    2
			lsp      param1
			lat      temp2
			leai     @temp3
			push    
			callk    StrCpy,  4
			push    
			callk    StrLen,  2
			push    
			pushi    80
			pushi    0
			lofsa    editI
			send     10
			jmp      code_0404
code_072e:
			pushi    1
			pushi    993
			callk    DisposeScript,  2
			pushi    #dispose
			pushi    0
			self     4
			pushi    1
			pushi    990
			callk    DisposeScript,  2
			lat      temp1
			ret     
		)
	)
	
	(method (dispose)
		(super dispose: &rest)
	)
)

; The Restore Game dialog.
(class Restore of SaveRestoreDialog
	(properties
		elements 0
		size 0
		text 0
		font 0
		window 0
		theItem 0
		nsTop 0
		nsLeft 0
		nsBottom 0
		nsRight 0
		time 0
		caller 0
		seconds 0
		lastSeconds 0
		eatTheMice 0
		lastTicks 0
	)
	
	(method (init)
		(Message msgGET 990 20 0 0 1 @local65)
		(= text @local65)
		(super init: &rest)
	)
)

;
; 	The Save Game dialog.
(class Save of SaveRestoreDialog
	(properties
		elements 0
		size 0
		text 0
		font 0
		window 0
		theItem 0
		nsTop 0
		nsLeft 0
		nsBottom 0
		nsRight 0
		time 0
		caller 0
		seconds 0
		lastSeconds 0
		eatTheMice 0
		lastTicks 0
	)
	
	(method (init)
		(Message msgGET 990 21 0 0 1 @local65)
		(= text @local65)
		(super init: &rest)
	)
)

(instance GetReplaceName of Dialog
	(properties)
	
	(method (doit param1 &tmp temp0 [temp1 15] [temp16 15] [temp31 15] [temp46 15])
		(= window gWindow)
		(Message msgGET 990 33 0 0 1 @temp1)
		(text1 text: @temp1 setSize: moveTo: 4 4)
		(self add: text1 setSize:)
		(oldName
			text: param1
			font: gSmallFont
			setSize:
			moveTo: 4 nsBottom
		)
		(self add: oldName setSize:)
		(Message msgGET 990 34 0 0 1 @temp16)
		(text2 text: @temp16 setSize: moveTo: 4 nsBottom)
		(self add: text2 setSize:)
		(newName
			text: param1
			font: gSmallFont
			setSize:
			moveTo: 4 nsBottom
		)
		(self add: newName setSize:)
		(Message msgGET 990 33 0 0 1 @temp31)
		(button1 text: @temp31 nsLeft: 0 nsTop: 0 setSize:)
		(Message msgGET 990 38 0 0 1 @temp46)
		(button2 text: @temp46 nsLeft: 0 nsTop: 0 setSize:)
		(button2
			moveTo: (- nsRight (+ (button2 nsRight?) 4)) nsBottom
		)
		(button1
			moveTo: (- (button2 nsLeft?) (+ (button1 nsRight?) 4)) nsBottom
		)
		(self add: button1 button2 setSize: center: open: 0 -1)
		(= temp0 (super doit: newName))
		(self dispose:)
		(if (not (StrLen param1))
			(NeedDescription)
			(= temp0 0)
		)
		(return (if (== temp0 newName) else (== temp0 button1)))
	)
)

(instance selectorI of DSelector
	(properties
		x 36
		y 8
	)
)

(instance editI of DEdit
	(properties
		max 35
	)
)

(instance okI of DButton
	(properties)
	
	(method (dispose)
		(super dispose: 1)
	)
)

(instance cancelI of DButton
	(properties)
	
	(method (dispose)
		(super dispose: 1)
	)
)

(instance changeDirI of DButton
	(properties)
	
	(method (dispose)
		(super dispose: 1)
	)
)

(instance deleteI of DButton
	(properties)
	
	(method (dispose)
		(super dispose: 1)
	)
)

(instance textI of DText
	(properties
		font 0
	)
	
	(method (dispose)
		(super dispose: 1)
	)
)

(instance text1 of DText
	(properties
		font 0
	)
	
	(method (dispose)
		(super dispose: 1)
	)
)

(instance text2 of DText
	(properties
		font 0
	)
	
	(method (dispose)
		(super dispose: 1)
	)
)

(instance oldName of DText
	(properties)
	
	(method (dispose)
		(super dispose: 1)
	)
)

(instance newName of DEdit
	(properties
		max 35
	)
)

(instance button1 of DButton
	(properties)
	
	(method (dispose)
		(super dispose: 1)
	)
)

(instance button2 of DButton
	(properties)
	
	(method (dispose)
		(super dispose: 1)
	)
)
