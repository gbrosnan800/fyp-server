<!DOCTYPE html>
<html>
<head>
    <title>G Brosnan - FYP</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script type="text/javascript" src="resources/js/jqueryFile.js"></script>    
    <script type="text/javascript" src="resources/js/jquery.jqplot.min.js"></script>
    <script type="text/javascript" src="resources/js/jqplot.barRenderer.min.js"></script>
    <script type="text/javascript" src="resources/js/jqplot.canvasTextRenderer.min.js"></script>
    <script type="text/javascript" src="resources/js/jqplot.canvasAxisTickRenderer.min.js"></script>
    <script type="text/javascript" src="resources/js/jqplot.categoryAxisRenderer.min.js"></script>
    <script type="text/javascript" src="resources/js/jqplot.pointLabels.min.js"></script>
    <script type="text/javascript" src="resources/js/main.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/jquery.jqplot.css"/>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css"/>
</head>
<body>
    <div id="menu">
        <div id="logo"><img src="resources/images/gbrosnan-fyp-white-logo.png" height="50"></div>
        <a href="" id="last_menu_item"><img src="resources/images/decision.png" height="26">DECISION MAKING</a>
        <a href=""><img src="resources/images/ann.png" height="26">ANN</a>
        <a href="analysis.jsp"><img src="resources/images/analysis.png" height="26">DATA ANALYSIS</a>
        <a href="index.jsp"><img src="resources/images/home.png" height="26">HOME</a>
    </div>
    <div id="control_panel_button"><div id="control_panel_button_img"><img src="resources/images/button-green.png"></div><div id="control_panel_button_txt">CONTROL PANEL</div></div>
    <div id="control_panel">
        <div id="control_panel_header">CONTROL PANEL<img src="resources/images/close.png"></div>
        <div id="control_panel_inner">
            <div id="control_panel_col_l">
                <div class="control_panel_menu_item" id="choose_collection">COLLECTIONS</div>
                <div class="control_panel_menu_item">CHOOSE SET</div>
            </div>
            <div id="control_panel_col_r">
                <div id="control_panel_col_r_header">Choose Collection</div>
                <div id="collection_list"></div>
                	
                	<div id="collect_set_list"></div>
                <!-- <div class="control_panel_col_r_col_item">TestData</div> -->
				
				<!-- 
					<div id="collect_set_list">
						<div id="collect_set_list_header">
							<div id="collect_set_list_header_id">ID</div>
							<div id="collect_set_list_header_name">NAME</div>
							<div id="collect_set_list_header_reps">REPS</div>
						</div>
						<div class="collect_set_list_item">
							<div class="collect_set_list_item_id">1</div>
							<div class="collect_set_list_item_name">bicep_curl</div>
							<div class="collect_set_list_item_reps">20</div>
						</div>		
					</div>
                -->
            </div>
        </div>
    </div>
    
    <div id="main_page_area">		
		<div id="graphs_area">
			<div class="graphs_area_section" id="graphs_area_raw">				
				<div class="graph_area_chart" id="graph_area_raw_chart"></div>
				<div class="graphs_area_section_menu" id="graphs_area_section_menu_raw">
					<div class="graph_menu_label">RAW DATA</div>
					<div class="graph_menu_item" id="view_averages">VIEW AVERAGES</div>	
				</div>
			</div>
			<div class="graphs_area_section" id="graphs_area_average">				
				<div class="graph_area_chart" id="graph_area_average_chart"></div>
				<div class="graphs_area_section_menu" id="graphs_area_section_average">
					<div class="graph_menu_label">AVERAGE DATA</div>
					<div class="graph_menu_item" id="average_data_menu">
						<div id="average_data_menu_img"><img src="resources/images/button-green.png"></div>
						<div id="average_data_menu_text">PEAK DETECTION</div>
					</div>	
					<div id="peak_detection_menu">
						<div class="peak_detection_menu_item" id="peak_detection_menu_item_maximas">
							<div class="peak_detection_menu_item_label" id="discover_maximas_btn">DISCOVER MAXIMAS</div>
							<div class="peak_detection_menu_item_info">START<input id="discover_maximas_start" value="25"></div>
							<div class="peak_detection_menu_item_info">RANGE<input id="discover_maximas_range" value="100"></div>
						</div>
						<div class="peak_detection_menu_item" id="peak_detection_menu_item_filterflat">
							<div class="peak_detection_menu_item_label" id="filter_flat_peaks_btn">FILTER FLAT PEAKS</div>
							<div class="peak_detection_menu_item_info">HEIGHT<input id="filter_flat_peaks_height" value="30">%</div>
							<div class="peak_detection_menu_item_info">RANGE<input id="filter_flat_peaks_range" value="100"></div>
						</div>
						<div class="peak_detection_menu_item" id="peak_detection_menu_item_filterout">
							<div class="peak_detection_menu_item_label" id="filter_outer_range_btn">FILTER OUTER-RANGE</div>
							<div class="peak_detection_menu_item_info">HEIGHT<input id="filter_outer_range_height" value="30">%</div>
						</div>
						<div class="peak_detection_menu_item" id="peak_detection_menu_item_minimas">
							<div class="peak_detection_menu_item_label" id="discover_minimas_btn">DISCOVER MINIMAS</div>
						</div>
						<div class="peak_detection_menu_item" id="peak_detection_menu_item_reps">
							<div class="peak_detection_menu_item_label" id="extract_reps_btn">EXTRACT REPS</div>
						</div>						
					</div>
				</div>
			</div>
			<div class="graphs_area_section" id="graphs_area_reps"></div>
			<div class="graphs_area_section" id="graphs_area_norm"></div>
		</div>
    </div>


</body>
</html>