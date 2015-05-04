<!DOCTYPE html>
<html>
<head>
    <title>G Brosnan - FYP</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="resources/css/home.css"/>
    <script type="text/javascript" src="resources/js/jqueryFile.js"></script>
    <script type="text/javascript" src="resources/js/home.js"></script>
</head>
<body>
    <div id="menu">
        <div id="logo"><img src="resources/images/gbrosnan-fyp-white-logo.png" height="50"></div>
        <div id="menu_link_container">
	        <a href="index.jsp"><img src="resources/images/home.png" height="26">HOME</a>
	        <a href="analysis.jsp"><img src="resources/images/analysis.png" height="26">DATA ANALYSIS</a>
	        <a href="ann.jsp"><img src="resources/images/ann.png" height="26">ANN</a>
	        <a href="feedback.jsp" id="last_menu_item"><img src="resources/images/decision.png" height="26">DECISION MAKING</a> 
        </div>
    </div>
	<div id="home">
		<div id="project_info">
			<div class="project_info_row">
				<div class="project_info_label">Name</div>
				<div class="project_info_detail">Gerry Bosnan</div>
			</div>
			<div class="project_info_row">
				<div class="project_info_label">College</div>
				<div class="project_info_detail">IT Tralee</div>
			</div>
			<div class="project_info_row">
				<div class="project_info_label">Course</div>
				<div class="project_info_detail">Bachelor (Hons) Of Science in Computing</div>
			</div>				
			<div class="project_info_row">
				<div class="project_info_label">Project Title</div>
				<div class="project_info_detail">Android Motion Sensor Capability <br>For Detecting, Tracking and Analysing Resistance-Training Exercises</div>
			</div>	
			<div class="project_info_row">
				<div class="project_info_label">SCM (Server)</div>
				<div class="project_info_detail"><a href="http://github.com/gbrosnan800/fyp-server">github.com/gbrosnan800/fyp-server</a></div>
			</div>
			<div class="project_info_row">
				<div class="project_info_label">SCM (Client)</div>
				<div class="project_info_detail"><a href="http://github.com/gbrosnan800/fyp-client" target="_blank">github.com/gbrosnan800/fyp-client</a></div>
			</div>	
			<div class="project_info_row">
				<div class="project_info_label">Thesis</div>
				<div class="project_info_detail"><a href="resources/documentation/FYP_GBrosnan_T00161582.pdf" target="_blank">Thesis.pdf</a></div>
			</div>	
		</div>
		<div id="youtube_container">
 			<iframe id="youtube_iframe" width="640" height="360"
				src="http://www.youtube.com/embed/cyvw6qwevTA">
			</iframe>	
		</div>
	</div>
	<div class="home_item">
		<div class="home_item_center">
			<div class="home_item_inner two_l">
				<h2>Overview</h2>
				<p>An Android Gym Application</p>
				<p>Tested on the Samsung Galaxy S3</p>
				<p>Sends Raw Data to an Apache Tomcat Server (Spring/Java)</p>
				<p>Hosted on an Ubuntu VM on Azure</p>
				<p>Server carries out Analysis &amp; Feedback</p>
				<p>Galaxy S3 receives Feedback</p>
			</div>	
			<div class="home_item_inner" id="one_r"><img src="resources/images/IMG_1555_web1.jpg"></div>
		</div>
	
	
	</div>

	<div class="home_item" style="background: #fff">
		<div class="home_item_center">
			<div class="home_item_inner two_l">
				<h2>Cardio vs Strength Training</h2>
				<p>Cardio training involves activities such as running, jogging, swimming etc.</p>
				<p>Strength training involves lifting heavy weights</p>
				<p><u>This project focused on strength training (resistance)</u></p>
				<p></p>
				<p></p>
			</div>
			<div class="home_item_inner two_l">
				<h2>Availability of Apps</h2>
				<p>Many apps already available</p>
				<p>Most apps focus on logging data to view progress</p>
				<p>Most Cardio apps take advantage of sensors</p>
				<p>Strength Training apps are basic (only manual input)</p>
				<p>This was a motivation for choosing this project</p>				
			</div>
		</div>
	</div>
	<div class="home_item">
		<div class="home_item_center">
			<div class="home_item_inner home_item_middle">
				<h2>Why use Sensors?</h2>
				<p><b>Sensors in Cardio apps can track more complex data</b><br>
				GPS tracks movement - Accelerometer counts steps and other movements</p>
				<p><b>With more complex data, more advanced analysis and user feedback is possible</b><br>
				Cardio apps can provide information on heart rate, calories burned, steps taken, etc. </p>
			</div>

		</div>
	</div>
	<div class="home_item" style="background: #fff">
		<div class="home_item_center">
			<h1 class="home_item_h1">What can Sensor technology bring to Strength-Training?</h1>
			<div class="home_item_inner two_l">
				<p><b>Detect, count and log Exercises</b><br></p>
				<p>The device detects and counts reps</p>
				<p>The user can concentrate on exercises while the device does the logging</p>
				<p>All activity can automatically be logged and saved to the cloud</p>
			</div>
			<div class="home_item_inner two_l">
				<p><b>Velocity-Based Training</b><br></p>
				<p>By timing repititions, a user's One-Rep-Max (1RM) can be calculated</p>
				<p>Based on 1RM and speed of last Rep, suggestions can guide the user to what weights to use week by week</p>
				<p>Uses the Brzycki Formula to make calculations</p>
			</div>
		</div>
	</div>
	<div class="home_item" style="background:#00a651;color:#fff">
		<div class="home_item_center" >
			<h1 class="home_item_h1">Methodology</h1>
			<div class="home_item_inner two_l" style="color:#fff;font-size:1.5em">
				<p><b>Neural Network</b><br>
				<p>Using Neuroph Framework</p>
				<p>Implementation of a Supervised Network</p>
				<p>Back Propagation with Momentum</p>					
			</div>
			<div class="home_item_inner two_l" style="color:#fff;font-size:1.5em">
				<p><b>Data Preprocessing</b><br>
				<p>Peak Detection</p>
				<p>Exctracting reps</p>
				<p>Transforming and Normalizing Data</p>
			</div>
		</div>
	</div>
	<div class="home_item" style="background: #fff">
		<div class="home_item_center">
			<div class="home_item_inner" id="one_r"><img src="resources/images/peaks.png"></div>
			<div class="home_item_inner two_l">
				<p><b>Peak Detection</b></p>				
				<p>Involved statistical analysis of data</p>
				<p>Data first transformed to one average series</p>
				<p>Comparing points with neighbouring points</p>
				<p>Filtering out of range peaks and flat peaks</p>
				<p>Reps Extracted from detected Maxima and Minima</p>					
			</div>
		</div>
	</div>
	<div class="home_item">
		<div class="home_item_center">	
			<div class="home_item_inner two_l">
				<p><b>Peak Detection Code Example</b></p>
				<p>A Loop to iterate each point in the raw data</p>
				<p>Helper methods to check if point is max value amoung neighbours</p>
								
			</div>
			<div class="home_item_inner" id="one_r"><img src="resources/images/peaks-code.png"></div>
		</div>
	</div>
	<div class="home_item" style="background: #fff">
		<div class="home_item_center">
			<div class="home_item_inner" id="one_r"><img src="resources/images/adjust_down.png"></div>
			<div class="home_item_inner two_l">
				<p><b>Adjusting Arrays</b></p>				
				<p>If the array was 250 but needed to be 200 for the ANN</p>
				<p>A difference of 50. Dived 250 by 50 = 5</p>
				<p>Remove every 5th item in the Array List</p>
				<p>Removed from end to start (to avoid the loop going out of bounds)</p>
				<p>The form of the Rep pattern remained after this action</p>					
			</div>
		</div>
	</div>	
	<div class="home_item">
		<div class="home_item_center">
			<h1 class="home_item_h1">Extracted Rep vs Extracted Normalized Rep</h1>		
			<div class="compare_adjust"><img src="resources/images/compare_adjust_orig.PNG"></div>
			<div class="compare_adjust"><img src="resources/images/compare_adjust.PNG"></div>
		</div>
	</div>
	<div class="home_item">
		<div class="home_item_center">
			<h1 class="home_item_h1">Appending Three Axes</h1>		
			<div class="threeaxischart"><img src="resources/images/extracted_3axis_bicep.PNG" class="threeaxischart"></div>		
			<div class="threeaxischart"><img src="resources/images/extracted_3axis_latraise.PNG" class="threeaxischart"></div>	
			<div class="threeaxischart"><img src="resources/images/extracted_3axis_kickback.PNG" class="threeaxischart"></div>
		</div>
	</div>
	<div class="home_item" style="background: #fff">
		<div class="home_item_center" id="ann_item">		
			<div class="home_item_inner home_item_middle">
				<h2>Implementing the Neural Network</h2>
				<img src="resources/images/createann.PNG" style="max-width:1250px;">
			</div>
		</div>
	</div>
	<div class="home_item" style="background: #000">
		<div class="home_item_center" id="ann_item">
			<h1 class="home_item_h1" style="color:#fff">Android Screenshots</h1>
			<div id="android_cont">			
				<div class="android_image"><img src="resources/images/android_dataset.png"></div>
				<div class="android_image"><img src="resources/images/android_routine.png"></div>
			</div>
		</div>
	</div>	 
	<div class="home_item" style="background: #00a651">
		<div class="home_item_center" id="ann_item">
			<div class="home_item_inner home_item_middle">
				<h2>Demonstrations and datasets in other Web Pages</h2>
			</div>
		</div>
	</div>	

	
	
	
</body>
</html>