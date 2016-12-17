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


#import "CM_emailAppt.h"


@implementation CM_emailAppt

//viewDidLoad
-(void)viewDidLoad{
	[BT_debugger showIt:self theMessage:@"viewDidLoad"];
	[super viewDidLoad];
    
    [_nameLabel sizeToFit];
    [_emailLabel sizeToFit];
    [_phoneLabel sizeToFit];
    [_schedBut setBackgroundColor:[BT_color getColorFromHexString:[BT_strings getStyleValueForScreen:self.screenData nameOfProperty:@"submitColor" defaultValue:@"#000000"]]];
    _schedBut.layer.cornerRadius = 5;
    [_schedBut setTitleColor:[BT_color getColorFromHexString:[BT_strings getStyleValueForScreen:self.screenData nameOfProperty:@"buttTextColor" defaultValue:@"#FFFFFF"]] forState:UIControlStateNormal ];
    
    
    self.toemail = [BT_strings getJsonPropertyValue:self.screenData.jsonVars
                                        nameOfProperty:@"emailAddr" defaultValue:@""];
    
    self.emailSubject = [BT_strings getJsonPropertyValue:self.screenData.jsonVars nameOfProperty:@"emailSubject" defaultValue:@"Appointment requested!"];
    
    self.nameLabel.textColor = [BT_color getColorFromHexString:[BT_strings getStyleValueForScreen:self.screenData nameOfProperty:@"textLabelColor" defaultValue:@"#000000"]];
    
    self.phoneLabel.textColor = [BT_color getColorFromHexString:[BT_strings getStyleValueForScreen:self.screenData nameOfProperty:@"textLabelColor" defaultValue:@"#000000"]];
    
    self.emailLabel.textColor = [BT_color getColorFromHexString:[BT_strings getStyleValueForScreen:self.screenData nameOfProperty:@"textLabelColor" defaultValue:@"#000000"]];
    
    UIToolbar* numberToolbar = [[UIToolbar alloc]initWithFrame:CGRectMake(0, 0, 320, 50)];
    numberToolbar.barStyle = UIBarStyleBlackTranslucent;
    numberToolbar.items = @[
                            [[UIBarButtonItem alloc]initWithTitle:@"Done" style:UIBarButtonItemStyleDone target:self action:@selector(doneWithNumberPad)]];
    [numberToolbar sizeToFit];
    _phone.inputAccessoryView = numberToolbar;

    

}

-(void)doneWithNumberPad{
    [_phone resignFirstResponder];
    
}

- (IBAction)sendEmail:(id)sender {
    NSArray *toRecipents = [NSArray arrayWithObject:@"cleancut@thelawnbarberz.com"];
    NSString *emailTitle = @"An appointment has been requested!";
    
    NSDate *myDate = _datePicker.date;
    
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"cccc, MMM d, hh:mm aa"];
    NSString *prettyVersion = [dateFormat stringFromDate:myDate];
    
    NSString *spacers =@"     \n";
  
    
    NSString *string1 = @"Date and time: ";
    string1 = [string1 stringByAppendingString:spacers];
    string1 = [string1 stringByAppendingString:prettyVersion];
    
    //extra space
    string1 = [string1 stringByAppendingString:spacers];
    string1 = [string1 stringByAppendingString:spacers];
    
    string1 = [string1 stringByAppendingString:@"Name:"];
    string1 = [string1 stringByAppendingString:spacers];
    string1 = [string1 stringByAppendingString:_name.text];
    
    //extra space
    string1 = [string1 stringByAppendingString:spacers];
    string1 = [string1 stringByAppendingString:spacers];
    
    string1 = [string1 stringByAppendingString:@"Phone number:"];
    string1 = [string1 stringByAppendingString:spacers];
    string1 = [string1 stringByAppendingString:_phone.text];
    
    NSString *messageBody = string1;
    
    [BT_debugger showIt:self theMessage:messageBody];
    MFMailComposeViewController *mc = [[MFMailComposeViewController alloc] init];
    mc.mailComposeDelegate = self;
    [mc setSubject:emailTitle];
    [mc setMessageBody:messageBody isHTML:NO];
    [mc setToRecipients:toRecipents];
    
    //check if they have configured email on there iOS device
    BT_appDelegate *appDelegate = (BT_appDelegate *)[[UIApplication sharedApplication] delegate];
    
    if([appDelegate.rootDevice canSendEmails]){
        [self presentViewController:mc animated:YES completion:NULL];
    }else{
        
        //show error message
        UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:NSLocalizedString(@"emailsNotSupportedTitle",@"Email Not Supported")
                                                            message:NSLocalizedString(@"emailsNotSupportedMessage", @"Sending eamils is not supported on this device.")
                                                           delegate:nil
                                                  cancelButtonTitle:NSLocalizedString(@"ok", "OK")
                                                  otherButtonTitles:nil];
        [alertView setTag:103];
        [alertView show];
        
    }

   // [self presentViewController:mc animated:YES completion:NULL];
}


-(BOOL) textFieldShouldReturn: (UITextField *) textField {
    [textField resignFirstResponder];
    return YES;
}

- (void) mailComposeController:(MFMailComposeViewController *)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError *)error
{
    switch (result)
    {
        case MFMailComposeResultCancelled:
            NSLog(@"Mail cancelled");
            break;
        case MFMailComposeResultSaved:
            NSLog(@"Mail saved");
            break;
        case MFMailComposeResultSent:
            NSLog(@"Mail sent");
            break;
        case MFMailComposeResultFailed:
            NSLog(@"Mail sent failure: %@", [error localizedDescription]);
            break;
        default:
            break;
    }
    
    // Close the Mail Interface
    [self dismissViewControllerAnimated:YES completion:NULL];
}

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    [self animateTextField: textField up: YES];
}


- (void)textFieldDidEndEditing:(UITextField *)textField
{
    [self animateTextField: textField up: NO];
}

- (void) animateTextField: (UITextField*) textField up: (BOOL) up
{
    const int movementDistance = 80; // tweak as needed
    const float movementDuration = 0.3f; // tweak as needed
    
    int movement = (up ? -movementDistance : movementDistance);
    
    [UIView beginAnimations: @"anim" context: nil];
    [UIView setAnimationBeginsFromCurrentState: YES];
    [UIView setAnimationDuration: movementDuration];
    self.view.frame = CGRectOffset(self.view.frame, 0, movement);
    [UIView commitAnimations];
}


@end







