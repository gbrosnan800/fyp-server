// JavaScript Document
    
 $(document).ready(function() {

	var setsList, maximasList;
	 
	assignInitialClicks();
	
	function assignInitialClicks() {
	    $('#control_panel_button').click(function(e){	
	    	e.stopPropagation();
	    	$('#peak_detection_menu').hide();
	    	$('#control_panel').animate({
	    		right:'0'
	    	});
	    	
	    });
	    $('#control_panel_header img').click(function(e){	 
	    	e.stopPropagation();
	    	$('#control_panel').animate({
	    		right:'-500'
	    	});	    	
	    });	    
	    $('#choose_collection').click(function(e){	
	    	e.stopPropagation();
	    	$('#control_panel_col_r_header').text('Choose Collection');
	    	getCollectionList();
	    });

	    $(document).click(function(e){	
	    	$('#peak_detection_menu').hide();
	    	$('#control_panel').animate({
	    		right:'-500'
	    	});	
	    });	

	    $('#average_data_menu').click(function(e){	
	    	e.stopPropagation();
	    	$('#peak_detection_menu').toggle();
	    });	 
	    
	    $('#peak_detection_menu div').click(function(e){	
	    	e.stopPropagation();
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
			if(!(collectionList[item] == 'exercises' || collectionList[item] == 'system.indexes')) {
				htmlString += '<div class="control_panel_col_r_col_item">' + collectionList[item]+ '</div>';
			}
		}
		$('#collection_list').html(htmlString);
		assignClicksToCollectionList();
	}
	
	function assignClicksToCollectionList() {

	    $('.control_panel_col_r_col_item').click(function(e){	
	    	e.stopPropagation();
	    	$('.control_panel_col_r_col_item').remove();
	    	var collectionname = $(this).text();
	    	$('#control_panel_col_r_header').text('Collection: ' + collectionname);
	        
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
			e.stopPropagation();
			
			$('.graph_area_chart').html('');
			$('.graphs_area_section_menu').fadeOut();

	    	var arrayNum = $(this).find('.collect_set_list_item_id').html();
	    	arrayNum--;
			$('#control_panel').animate({
	    		right:'-500',
	    	});	
			$('#graphs_area_section_menu_raw').fadeIn();
	    	plotGraph_RawData(setsList[arrayNum]);
	    	assignClickToRawGraphMenu(setsList[arrayNum]);
		});
	}
	
	function assignClickToRawGraphMenu(exericse) {
		$('#view_averages').unbind('click');
		$('#view_averages').click(function(e){			
			$('#graphs_area_section_average').show();
			var maximas = [];
			plotGraph_AverageData(exericse, maximas);
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
	
	function plotGraph_AverageData(exericse, maximas) {
		var sampleList = exericse.sensorSampleList;
		var averages = [];
		var average = 0;
		var labelPoints = [];

		for(var point = 0 ; point < sampleList.length ; point++ ) {
			average = (sampleList[point].x + sampleList[point].y + sampleList[point].z) / 3;					
			averages[point] = average;
			if(maximas.indexOf(point) > -1) {
				labelPoints[point] = 'M';
			}
			else {
				labelPoints[point] = null;
			}
		}
		plotGraphWithLabelPoints(averages, labelPoints);
		assignClickToDiscoverMaxiams(averages);
	}

	function plotGraphWithLabelPoints(averages, labelPoints) {
				
		$('#graph_area_average_chart').html('');
		
		$.jqplot('graph_area_average_chart',  [averages], { 	
			
			title: false,
			animate: false,
			/*series: [{color:'#00a651'}]	,*/
			series: [{
				smooth:true,
				lineWidth: 4,
				color: '#00a651',
				showMarker:false,
				pointLabels: {
					show: true,		
					labels: labelPoints
				},
			}],
			axes: {
				xaxis: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					tickOptions: {
						angle:-45,
						fontSize:'10pt',
						textColor: '#ffffff'
					},
					pad:0,
				},
				yaxis: {
					pad:1.5,
					showTicks:false
				}
			},
			grid: {
				gridLineColor:'#333333',
				background:'#000'
			}
		});
				  
	}	

	function assignClickToDiscoverMaxiams(averages) {
		$('#discover_maximas_btn').unbind('click');
		$('#discover_maximas_btn').click(function(e){				
			
			assignClicksToFiltersAndMinimas(averages)
			
			var start = $('#discover_maximas_start').val();
			var range = $('#discover_maximas_range').val();			
			var averageList = {
					"averages" : averages,
					"start" : start,
					"range" : range,
			}
			requestPeakData(averages, averageList, 'maximas')


			
			
		});

	}
	
	function requestPeakData(averages, averageList, urlEnd) {
        $.ajax({
            url: 'rest/data/' + urlEnd,
            type: 'POST',
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            data: JSON.stringify(averageList),
            success: function(response) {		            	
            	maximasList = response;
            	var labelPoints = [];
            	for(var point = 0 ; point < averages.length ; point ++) {
        			if(response.indexOf(point) > -1) {
        				labelPoints[point] = 'MAX';
        			}
        			else {
        				labelPoints[point] = null;
        			}
            	}            	            
            	plotGraphWithLabelPoints(averages, labelPoints);
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                alert(err.Message);
            }
        });	
	}
	
	
	function assignClicksToFiltersAndMinimas(averages) {			
		
		$('#peak_detection_menu_item_filterflat').css('opacity',"1");
		$('#peak_detection_menu_item_filterout').css('opacity',"1");
		$('#peak_detection_menu_item_minimas').css('opacity',"1");		
		$('#filter_flat_peaks_btn').css('cursor','pointer');
		$('#filter_outer_range_btn').css('cursor','pointer');
		$('#filter_outer_range_btn').css('cursor','pointer');
		$('#discover_minimas_btn').css('cursor','pointer');
		
		$('#extract_reps_btn').unbind('click'); 
		$('#extract_reps_btn').css('cursor','auto'); 
		$('#peak_detection_menu_item_reps').css('opacity',"0.2");
		
		$('#filter_flat_peaks_btn').unbind('click');
		$('#filter_flat_peaks_btn').click(function(e){ 
			
			var height = $('#filter_flat_peaks_height').val();
			var range = $('#filter_flat_peaks_range').val();			
			var averageList = {
					"averages" : averages,
					"maximas" : maximasList,
					"range" : range,
					"height": height/100
			}
			requestPeakData(averages, averageList, 'filterflatpeaks');
			
		});
		$('#filter_outer_range_btn').unbind('click');
		$('#filter_outer_range_btn').click(function(e){ 
			
			var height = $('#filter_outer_range_height').val();		
			var averageList = {
					"averages" : averages,
					"maximas" : maximasList,
					"height": height/100
			}
			requestPeakData(averages, averageList, 'filterouterrange');
			
		});		
		$('#discover_minimas_btn').unbind('click');
		$('#discover_minimas_btn').click(function(e){ 
			
			$('#peak_detection_menu_item_reps').css('opacity',"1");
			$('#extract_reps_btn').css('cursor','pointer'); 
			getMinimas(averages);
			
			
			//EXTRACT REPS CLICK
			$('#extract_reps_btn').unbind('click');
			$('#extract_reps_btn').click(function(e){ 
			
				$('#peak_detection_menu').hide();
				$('#graphs_area_section_menu_reps').show();
				var averageList = {
						"averages" : averages,
						"maximas" : maximasList,
				}
				
		        $.ajax({
		            url: 'rest/data/reps',
		            type: 'POST',
		            contentType : 'application/json; charset=utf-8',
		            dataType : 'json',
		            data: JSON.stringify(averageList),
		            success: function(response) {		            	
		            	plotReps(response, '#graph_area_rep_chart', 'repDiv');
		            	assignClickToNorm(response);
		            },
		            error: function(xhr, status, error) {
		                var err = eval("(" + xhr.responseText + ")");
		                alert(err.Message);
		            }
		        });	

			});	
			
			
			
		});		
		
		
		
	}
	
	
	function getMinimas(averages) {
		
		var startPoint, endPoint;
		var minimas = [];
		
		for(var point in maximasList) {
			
			endPoint = maximasList[point];
			if( point == 0) {
				startPoint = 0;				
			}
			else {
				startPoint = maximasList[point - 1];
			}
			
			minimas[point] = discoverMinima(averages, startPoint, endPoint)
		}		
		minimas.push(discoverMinima(averages, maximasList[maximasList.length - 1], averages.length - 1))		
		var maxAndMins = minimas.concat(maximasList);
		maxAndMins.sort(function(a,b){return a-b});

		var labelPoints = [];
    	for(var point = 0 ; point < averages.length ; point ++) {
			if(maxAndMins.indexOf(point) > -1) {
				labelPoints[point] = 'M';
			}
			else {
				labelPoints[point] = null;
			}
    	}   
		plotGraphWithLabelPoints(averages, labelPoints);
	}
	
	function discoverMinima(averages, startPoint, endPoint) {
		
		var lowestPoint = 0;
		var lowestValue = 10000000;
		for(var point = startPoint ; point < endPoint ; point ++) {
			
			if(averages[point] < lowestValue) {
				lowestValue = averages[point];
				lowestPoint = point;
			}
		}
		return lowestPoint;
	}
	
	function assignClickToNorm(exercise) {

		$('#view_norm').unbind('click');
		$('#view_norm').click(function(e){ 
			
			$('#graphs_area_section_menu_norm').show();
			plotReps(exercise, '#graph_area_repnorm_chart', 'repDivNorm') 				
		});
	}
	
	//PLOT REPS
	
	function plotReps(exercise, chart, repDiv) {
	
		var reps;
		if(repDiv == 'repDivNorm') {
			console.log('true');
			reps = exercise.normalisedReps;
		}
		else {
			reps = exercise.extractedReps;
		}
		
		for(var num in reps) {			
			console.log(reps[num].samples.length);
		}
		
		
		var width = $(chart).width()
		var widthOfRep = width / reps.length - 10 ;
		
		var htmlString = '';
		for(var num in reps) {						
			htmlString += '<div class="' + repDiv + '" id="' + repDiv + num + '"></div>';
		}
		$(chart).html(htmlString);
		var divName = '.' + repDiv
		$(divName).css('float', 'left');
		$(divName).css('height', '120px');
		//$(divName).css('width', widthOfRep + 'px');
		$(divName).css('width', '100px');
		$(divName).css('margin-right', '5px');

		for(var num in reps) {			
			plotRep(repDiv, reps[num], num);
		}
	}
	
	function plotRep(divName, rep, num) {
		
		$.jqplot(divName + num,  [rep.samples], { 	
			
			title: false,
			animate: false,
			series: [{
				smooth:true,
				lineWidth: 4,
				color: '#00a651',
				showMarker:false,
				pointLabels: {
					show: false,		
				},
			}],
			axes: {
				xaxis: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					tickOptions: {
						angle:-45,
						fontSize:'10pt',
						textColor: '#ffffff'
					},
					pad:0,
				},
				yaxis: {
					pad:1.5,
					showTicks:true,
					textColor:'#ffffff'
				}
			},
			grid: {
				gridLineColor:'#333333',
				background:'#000'
			}
		});
				  
	}
	
	
	
	
	
	
}); // end ready 


