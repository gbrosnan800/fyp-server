// JavaScript Document

    
 $(document).ready(function() {

	 var setsList;
	 
	
	assignInitialClicks();
	
	function assignInitialClicks() {
	    $('#control_panel_button').click(function(e){	    	
	    	$('#control_panel').animate({
	    		right:'0'
	    	});
	    	
	    });
	    $('#control_panel_header img').click(function(e){	    		
	    	$('#control_panel').animate({
	    		right:'-500'
	    	});	    	
	    });
	    
	    $('#choose_collection').click(function(e){	    	
	    	getCollectionList();
	    });
	}
	
	function getCollectionList() {
        $.ajax({
            url: 'rest/data/collectionnames',
            type: 'GET',
            success: function(collectionList) {
            	displayCollectionList(collectionList);
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                alert(err.Message);
            }
        });
	}
	
	function displayCollectionList(collectionList) {
		
		$('#collect_set_list div').remove();
		var htmlString = '';
		for(var item in collectionList) {
			htmlString += '<div class="control_panel_col_r_col_item">' + collectionList[item]+ '</div>';
		}
		$('#collection_list').html(htmlString);
		assignClicksToCollectionList();
	}
	
	function assignClicksToCollectionList() {

	    $('.control_panel_col_r_col_item').click(function(e){	
	    	$('.control_panel_col_r_col_item').remove();
	    	var collectionname = $(this).text();
	        
	    	$.ajax({
	            url: 'rest/data/' + collectionname + '/sets',
	            type: 'GET',
	            success: function(sets) {
	            	setsList = sets;
	            	displaySetList();
	            },
	            error: function(xhr, status, error) {
	                var err = eval("(" + xhr.responseText + ")");
	                alert(err.Message);
	                return null;
	            }
	        });	    	
	    });		
	}
	 
	function displaySetList() {
		
		var htmlString = '';
		htmlString += '' +				
		'<div id="collect_set_list_header">' +
			'<div id="collect_set_list_header_id">ID</div>' +
			'<div id="collect_set_list_header_name">NAME</div>' +
			'<div id="collect_set_list_header_reps">REPS</div>' +
		'</div>';
		
		for(var item in setsList) {
			htmlString += 
				'<div class="collect_set_list_item">' +
					'<div class="collect_set_list_item_id">' + setsList[item].id + '</div>' +
					'<div class="collect_set_list_item_name">' + setsList[item].exerciseName + '</div>' +
					'<div class="collect_set_list_item_reps">' + setsList[item].repCount + '</div>' +
				'</div>';
		}
		$('#collect_set_list').html(htmlString);
		
		assignClicksToSets();
	}
	
	function assignClicksToSets() {
		$('.collect_set_list_item').click(function(e){	
	    
	    	var arrayNum = $(this).find('.collect_set_list_item_id').html();
	    	arrayNum--;
			$('#control_panel').animate({
	    		right:'-500',
	    	});	
	    	plotGraph_RawData(setsList[arrayNum]);
	    	assignClickToRawGraphMenu(setsList[arrayNum]);
		});
	}
	
	function assignClickToRawGraphMenu(exericse) {
		$('#view_averages').click(function(e){	
			plotGraph_AverageData(exericse);
	    	
		});
	}
	
	function plotGraph_RawData(exericse) {
		
		var sampleList = exericse.sensorSampleList;
		var x = [], y = [], z = [];
			
		for(var sample in sampleList) {			
			x[sample] = sampleList[sample].x;
			y[sample] = sampleList[sample].y;
			z[sample] = sampleList[sample].z;
		}
		
		$('#graph_area_raw_chart').html('');
	
		$.jqplot('graph_area_raw_chart',  [x,y,z], { 	
			
			title: false,
			animate: true,
			series: [{color:'#00a651'}]	,
			seriesDefaults: {
				smooth:true,
				lineWidth: 4,
				showMarker:false,
			},
			axes: {
				xaxis: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					tickOptions: {
						angle:-45,
						fontSize:'10pt',
						textColor: '#ffffff'
					},
					pad:0
				},
				yaxis: {
					pad:0,
					showTicks:false
				}
			},
			grid: {
				gridLineColor:'#333333',
				background:'#000'
			}
		});
	}
	

	function plotGraph_AverageData(exericse) {
		var sampleList = exericse.sensorSampleList;
		var averages = [];
		var average = 0;
			
		for(var sample in sampleList) {			
			average = (sampleList[sample].x + sampleList[sample].y + sampleList[sample].z) / 3;					
			averages[sample] = average;
		}
		
		$('#graph_area_average_chart').html('');
	
		$.jqplot('graph_area_average_chart',  [averages], { 	
			
			title: false,
			animate: true,
			series: [{color:'#00a651'}]	,
			seriesDefaults: {
				smooth:true,
				lineWidth: 4,
				showMarker:false,
				/*pointLabels: {
					show: true,
					labels: points
				},*/
			},
			axes: {
				xaxis: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					tickOptions: {
						angle:-45,
						fontSize:'10pt',
						textColor: '#ffffff'
					},
					pad:0
				},
				yaxis: {
					pad:0,
					showTicks:false
				}
			},
			grid: {
				gridLineColor:'#333333',
				background:'#000'
			}
		});
				  
	}
	
	
	
	



  
}); // end ready 


