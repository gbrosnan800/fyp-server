<!DOCTYPE html>
<html>
<head>
    <title>G Brosnan - FYP</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <script type="text/javascript" src="resources/js/jqueryFile.js"></script> 
    <script type="text/javascript" src="resources/js/ann.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/ann.css"/>
</head>
<body>
    <div id="menu">
        <div id="logo"><img src="resources/images/gbrosnan-fyp-white-logo.png" height="50"></div>
        <div id="menu_link_container">
	        <a href="index.jsp"><img src="resources/images/home.png" height="26">HOME</a>
	        <a href="analysis.jsp"><img src="resources/images/analysis.png" height="26">DATA ANALYSIS</a>
	        <a href="ann.jsp"><img src="resources/images/ann.png" height="26">ANN</a>
	        <a href="" id="last_menu_item"><img src="resources/images/decision.png" height="26">DECISION MAKING</a> 
        </div>
    </div>
    
    <div id="main_ann_section">
    	<div id="main_ann_section_inner">	    	
	    	<div id="ann_menu">
	    		<div class="ann_menu_item_outer"><div class="ann_menu_item">CREATE CSV</div></div>
	    		<div class="ann_menu_item_outer"><div class="ann_menu_item">TRAIN NETWORK</div></div>
	    	</div>
	    	
	    	<div id="csv_area">    	 
	    		<div class="csv_area_col">
	    			<div class="csv_area_col_header">Choose Collection</div>
	    			<div id="collection_list"></div>
	    		</div>
	    		<div class="csv_area_col" id="sets_col">
	    			<div class="csv_area_col_header">Select Sets</div>
	    			<div id="sets_list"></div>	    			
	    		</div>
	    		<div class="csv_area_col">
	    			<div class="csv_area_col_header">Pre-Process Config</div>	    			
	    			<div id="pre-process_config">	    				
	    				<div class="pre-process_config_item">
							<div class="pre-process_config_header" id="discover_maximas_btn">Discover Maximas</div>
							<div class="pre-process_config_label">START<input id="discover_maximas_start" value="25"></div>
							<div class="pre-process_config_label">RANGE<input id="discover_maximas_range" value="100"></div>
						</div>
						<div class="pre-process_config_item">
							<div class="pre-process_config_header">Filter Flat PeaKs</div>
							<div class="pre-process_config_label">HEIGHT<input id="filter_flat_peaks_height" value="20">%</div>
							<div class="pre-process_config_label">RANGE<input id="filter_flat_peaks_range" value="100"></div>
						</div>
						<div class="pre-process_config_item">
							<div class="pre-process_config_header" id="filter_outer_range_btn">Filter Outer-Range</div>
							<div class="pre-process_config_label">HEIGHT<input id="filter_outer_range_height" value="30">%</div>
						</div>
	    			</div>
	    			<div id="filename_area">
	    				<div id="filename_label">Filename<input value="newfile"><span style="color:#00a651;font-weight:bold">.csv</span></div>
	    			</div>
	    			<div id="create_csv_button">CREATE CSV</div>
	    			<div id="csv_status">STATUS:<div id="csv_status_msg"></div>No message</div>
	    			<div id="csv_exercise_list">
	    				<div id="csv_exercise_list_title">Current List<div id="clear_list">[ CLEAR ]</div></div>
	    				<div id="csv_exercise_list_header">
	    					<div class="csv_exercise_list_header_output">OUTPUT</div>
	    					<div class="csv_exercise_list_header_exercise">EXERCISE</div>
	    					<div class="csv_exercise_list_header_reps">REPS</div>	    					
	    				</div>
	    				<div id="current_list">No exercises</div>
	    				<!-- 
	    				<div class="csv_exercise_list_item">
	    					<div class="csv_exercise_list_item_output">001</div>
	    					<div class="csv_exercise_list_item_exercise">bicep_curl</div>
	    					<div class="csv_exercise_list_item_reps">25</div>
	    				</div>
	    				-->
	    			</div>
	    			
	    		<!-- 
	    			
	    			Config
	    				-Discover Maximas = start & range
	    				-Filter Flat = height and Range
	    				-Filter outer = height	    				
	    				
	    				-Will have to seend the above data plus exercise info (array of collection and id) + file name
	    				-Server will also then discover minimas, extract reps, normalize them
	    				-Then Server will create CSV and add normalized data plus output  - and with file name given
	    				-Return confirmation to webpage and offer download option
	    				
	    				

	    				
	    				
	    				
	    		 -->
	    			
	    			
	    		</div>		    	 
	    	</div>	    	
	    	<div id="ann_area">
	    	
	    	
	    	</div>
    		
    
    	</div>
    </div>
    

</body>
</html>