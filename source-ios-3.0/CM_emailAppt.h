/*
 *	Copyright 2016, Christopher Coffee
 *
 *	All rights reserved.
 *
 *	Redistribution and use in source and binary forms, with or without modification, are 
 *	permitted provided that the following conditions are met:
 *
 *	Redistributions of source code must retain the above copyright notice which includes the
 *	name(s) of the copyright holders. It must also retain this list of conditions and the 
 *	following disclaimer. 
 *
 *	Redistributions in binary form must reproduce the above copyright notice, this list 
 *	of conditions and the following disclaimer in the documentation and/or other materials 
 *	provided with the distribution. 
 *
 *	Neither the name of David Book, or buzztouch.com nor the names of its contributors 
 *	may be used to endorse or promote products derived from this software without specific 
 *	prior written permission.
 *
 *	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 *	ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 *	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 *	IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 *	INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
 *	NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 *	WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 *	ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 *	OF SUCH DAMAGE. 
 */

#import "BT_viewController.h"

@interface CM_emailAppt : BT_viewController<MFMailComposeViewControllerDelegate,UITextFieldDelegate> {
	

}

- (IBAction)sendEmail:(id)sender;

@property(nonatomic,retain)NSString *toemail;
@property(nonatomic,retain)NSString *emailAddress;
@property(nonatomic,retain)NSString *emailBody;
@property(nonatomic,retain)NSString *emailSubject;

@property (nonatomic, weak) IBOutlet UIDatePicker *datePicker;
@property (nonatomic, weak) IBOutlet UITextField *name;
@property (nonatomic, weak) IBOutlet UITextField *phone;
@property (nonatomic, weak) IBOutlet UITextField *email;
@property (nonatomic, weak) IBOutlet UIButton *schedBut;

@property (nonatomic, weak) IBOutlet UILabel *nameLabel;
@property (nonatomic, weak) IBOutlet UILabel *emailLabel;
@property (nonatomic, weak) IBOutlet UILabel *phoneLabel;

@end










