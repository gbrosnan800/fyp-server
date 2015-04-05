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
	        <a href="feedback.jsp" id="last_menu_item"><img src="resources/images/decision.png" height="26">DECISION MAKING</a> 
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
	    				<div id="filename_label">Filename<input value="newfile" id="fileName"><span style="color:#00a651;font-weight:bold">.csv</span></div>
	    			</div>
	    			<div id="create_csv_button">CREATE CSV</div>
	    			<div id="csv_status">STATUS:<div id="csv_status_msg">No Message</div></div>
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
	    		</div>		    	 
	    	</div>	    	
	    	<div id="ann_area">
	    		<div id="ann_options_column">
	    			<div class="dropdown_label">CHOOSE TRAINING DATA</div>
	    			<div id="dropdown_trainfile"><div id="dropdown_trainfile_selected">defaulttrain.csv</div>
	    				<div id="dropdown_arrow_trainfile"><img src="resources/images/droparrow.png"></div>
		    			<div id="dropdown_menu_trainfile">
		    				<div class="ann_file_menu_item">another_file1.csv</div>
		    				<div class="ann_file_menu_item">another_file2.csv</div>				
		    			</div>
	    			</div>
	    			<div class="dropdown_label">CHOOSE TESTING DATA</div>
	    			<div id="dropdown_testfile"><div id="dropdown_testfile_selected">defaulttest.csv</div>
	    				<div id="dropdown_arrow_testfile"><img src="resources/images/droparrow.png"></div>
		    			<div id="dropdown_menu_testfile">
		    				<div class="ann_file_menu_item">another_file1.csv</div>
		    				<div class="ann_file_menu_item">another_file2.csv</div>			    				
		    			</div>
	    			</div>
	    			<div id="ann_config_header">ANN CONFIG</div>
		    		<div id="ann_config">	
		    			<div class="ann_config_item">
		    				<div class="ann_config_item_label">Input Perceptrons</div>
		    				<div class="ann_config_item_input_container"><input id="input_perceptrons" value="600"></div>
		    			</div>	    				    		
		    			<div class="ann_config_item">
		    				<div class="ann_config_item_label">Hidden Perceptrons</div>
		    				<div class="ann_config_item_input_container"><input id="hidden_perceptrons" value="300"></div>
		    			</div>
		    			<div class="ann_config_item">
		    				<div class="ann_config_item_label">Output Perceptrons</div>
		    				<div class="ann_config_item_input_container"><input id="output_perceptrons" value="3"></div>
		    			</div>
		    			<div class="ann_config_item">
		    				<div class="ann_config_item_label">Learning Rate</div>
		    				<div class="ann_config_item_input_container"><input id="learning_rate" value="0.2"></div>
		    			</div>
		    			<div class="ann_config_item">
		    				<div class="ann_config_item_label">Momentum</div>
		    				<div class="ann_config_item_input_container"><input id="momentum" value="0.9"></div>
		    			</div>
		    			<div class="ann_config_item">
		    				<div class="ann_config_item_label">Max Iterations</div>
		    				<div class="ann_config_item_input_container"><input id="max_iterations" value="1000"></div>
		    			</div>		    			
		    		</div>
		    		<div id="train_button">TRAIN NETWORK</div>
	    		</div>
	    		<div id="ann_result_column">
	    			<div id="ann_status_message">Network not trained</div>
	    			<div id="results_button">VIEW RESULTS</div>
	    			<div id="ann_results">
	    				<div id="ann_results_close">X</div>
	    				<div id="ann_results_precent">-%</div>	    					    				
		    			<div id="ann_results_items">			    					    			
		    				<div class="ann_results_num ann_results_header">Num</div>
		    				<div class="ann_results_expected ann_results_header">Excpected</div>
		    				<div class="ann_results_actual ann_results_header">Actual</div>
		    				<div id="ann_results_render"></div>
	    				</div>
	    			</div>
	    		</div>	    	
	    	</div>    		   
    	</div>
    </div>
    

</body>
</html>