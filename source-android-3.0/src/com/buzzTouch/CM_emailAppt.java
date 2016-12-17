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
package com.buzzTouch;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;


public class CM_emailAppt extends BT_fragment {
	Button time;
	Button date;
	Button sched;

	TextView timeView;
	TextView dateView;


	EditText phoneView;
	EditText nameView;

	String finalTime;
	String finalDate;

	OnDateSetListener ondate;
	int pYear;
	int pMonth;
	int pDay;

	OnTimeSetListener ontime;
	int tpH;
	int tpM;
	//onCreateView...
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState){

		/*
			Note: fragmentName property is already setup in the parent class (BT_fragment). This allows us
			to add the 	name of this class file to the LogCat console using the BT_debugger.
		*/
		//show life-cycle event in LogCat console...
		BT_debugger.showIt(fragmentName + ":onCreateView JSON itemId: \"" + screenData.getItemId() + "\" itemType: \"" + screenData.getItemType() + "\" itemNickname: \"" + screenData.getItemNickname() + "\"");

		//inflate the layout file for this screen...
		View thisScreensView = inflater.inflate(R.layout.cm_emailappt, container, false);
	
		//show a long toast so user knows this screen is blank intentionally...
		//showToast("This screen is blank intentionally. See CM_emailAppt.java to see how little is required to launch a simple plugin.", "long");

		final String subjectTitle = BT_strings.getJsonPropertyValue(this.screenData.getJsonObject(), "emailSubject", "Appointment Requested!");
		final String recipientEmail = BT_strings.getJsonPropertyValue(this.screenData.getJsonObject(), "emailAddr", "");
		String textLabel = BT_strings.getJsonPropertyValue(this.screenData.getJsonObject(), "textLabelColor", "#000000");
		String submitColor = BT_strings.getJsonPropertyValue(this.screenData.getJsonObject(), "submitColor", "#FFFFFF");
		String buttTextColor = BT_strings.getJsonPropertyValue(this.screenData.getJsonObject(), "buttTextColor", "#000000");
		String dateTimeColor = BT_strings.getJsonPropertyValue(this.screenData.getJsonObject(), "dateTimeColor", "#FFFFFF");


		nameView = (EditText) thisScreensView.findViewById(R.id.editText);
		phoneView = (EditText) thisScreensView.findViewById(R.id.editText2);

		dateView = (TextView)thisScreensView.findViewById(R.id.textView4);
		dateView.setTextColor(Color.parseColor(textLabel));

		timeView = (TextView)thisScreensView.findViewById(R.id.textView3);
		timeView.setTextColor(Color.parseColor(textLabel));


		time = (Button)thisScreensView.findViewById(R.id.button2);
		time.setTextColor(Color.parseColor(buttTextColor));

		time.setPadding(15,0,15,0);
		time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				TimePickerFragment date = new TimePickerFragment();

				Calendar calender = Calendar.getInstance();
				Bundle args = new Bundle();

				args.putInt("hour", calender.get(Calendar.HOUR_OF_DAY));
				args.putInt("minute", calender.get(Calendar.MINUTE));
				date.setArguments(args);

				date.setCallBack(ontime);
				date.show(getFragmentManager(), "Date Picker");

			}
		});
		GradientDrawable shape =  new GradientDrawable();
		shape.setCornerRadius( 8 );
		Drawable d = shape;
		shape.setColor(Color.parseColor(dateTimeColor));
		time.setBackgroundDrawable(shape);


		ontime = new OnTimeSetListener() {

			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub

				Calendar calendar = Calendar.getInstance();
				tpH=hourOfDay;
				tpM=minute;

				calendar.set(Calendar.HOUR,tpH);
				calendar.set(Calendar.MINUTE, tpM);

				String finalHr;
				String finalMin;
				String ampm;

				if(tpH < 12)
				{
					ampm = "AM";
				}
				else
				{
					ampm = "PM";
				}

				if(tpH > 12)
				{
					int subHour = tpH - 12;
					finalHr = String.valueOf(subHour);
				}
				else
				{
					finalHr = String.valueOf(tpH);
				}

				if(tpM < 10)
				{
					finalMin = "0" + tpM;
				}
				else
				{
					finalMin = String.valueOf(tpM);
				}

				finalTime = finalHr + ":" + finalMin + " " + ampm;

				timeView.setText(finalTime);

			}
		};

		date = (Button)thisScreensView.findViewById(R.id.button);
		date.setTextColor(Color.parseColor(buttTextColor));
		date.setPadding(15,0,15,0);
		date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerFragment date = new DatePickerFragment();

				Calendar calender = Calendar.getInstance();
				Bundle args = new Bundle();

				args.putInt("year", calender.get(Calendar.YEAR));
				args.putInt("month", calender.get(Calendar.MONTH));
				args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
				date.setArguments(args);

				date.setCallBack(ondate);
				date.show(getFragmentManager(), "Date Picker");
				int year = date.year;
			}
		});
		GradientDrawable shape2 =  new GradientDrawable();
		shape2.setCornerRadius( 8 );
		shape2.setColor(Color.parseColor(dateTimeColor));
		date.setBackgroundDrawable(shape2);
		date.setTextColor(Color.parseColor(dateTimeColor));

		ondate = new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
								  int dayOfMonth) {

				Calendar calendar = Calendar.getInstance();
				pYear=year;
				pMonth=monthOfYear;
				pDay=dayOfMonth;

				calendar.set(year, monthOfYear, dayOfMonth);

				String monthLongName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

				finalDate = monthLongName + " " + pDay + "," + " " + pYear;
				dateView.setText(finalDate);

			}
		};


		sched = (Button)thisScreensView.findViewById(R.id.button3);
		sched.setPadding(25,0,25,0);
		sched.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] toAddress = {recipientEmail};

				String body = "Name: " + nameView.getText() + "\n" +
						"Phone: " + phoneView.getText() + "\n" +
						"Date: " + finalDate + "\n" +
						"Time: " + finalTime ;

				composeEmail(toAddress, subjectTitle, body);
			}
		});

		GradientDrawable shape3 =  new GradientDrawable();
		shape3.setCornerRadius( 8 );
		shape3.setColor(Color.DKGRAY);
		sched.setBackgroundDrawable(shape3);
		sched.setTextColor(Color.parseColor(submitColor));

		//return the layout file as the view for this screen..
		return thisScreensView;


	}//onCreateView...



	public static class DatePickerFragment extends DialogFragment {
		OnDateSetListener ondateSet;

		public DatePickerFragment() {

		}

		public void setCallBack(OnDateSetListener ondate) {
			ondateSet = ondate;
		}

		private int year, month, day;

		@Override
		public void setArguments(Bundle args) {
			super.setArguments(args);
			year = args.getInt("year");
			month = args.getInt("month");
			day = args.getInt("day");
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
		}
	}

	public static class TimePickerFragment extends DialogFragment {
		OnTimeSetListener onTimeSet;
		private int hour, minute;

		public TimePickerFragment() {

		}

		public void setCallBack(OnTimeSetListener onTime) {
			onTimeSet = onTime;
		}


		@Override
		public void setArguments(Bundle args) {
			super.setArguments(args);
			hour = args.getInt("hour");
			minute = args.getInt("minute");
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new TimePickerDialog(getActivity(), onTimeSet, hour, minute, false);
		}
	}

	public void composeEmail(String[] addresses, String subject, String body) {

		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setData(Uri.parse("mailto:")); // only email apps should handle this
		intent.putExtra(Intent.EXTRA_EMAIL, addresses);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, body);
		if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
			startActivity(intent);
		}
	}

}


 


