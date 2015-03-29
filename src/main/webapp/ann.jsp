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
	    			<div id="collection_list">
	    				<div class="csv_area_collection_item">item</div>
	    			</div>
	    		</div>
	    		<div class="csv_area_col" id="sets_col">
	    			<div class="csv_area_col_header">Select Sets</div>
	    			<div id="sets_list">
	    				<div class="csv_area_sets_item">	    		
	    					<div class="csv_area_sets_item_id">1</div>
	    					<div class="csv_area_sets_item_name">item</div>
	    					<div class="csv_area_sets_item_reps">10</div>	    					
	    				</div>
						<br><br>selected items turn green
						<br><br>use a jquery scroll bar
	    			</div>	    			
	    		</div>
	    		<div class="csv_area_col">
	    			<div class="csv_area_col_header">Pre-Process Config</div>
	    			
	    		</div>		    	 
	    	</div>	    	
	    	<div id="ann_area">
	    	
	    	
	    	</div>
    		
    
    	</div>
    </div>
    

</body>
</html>