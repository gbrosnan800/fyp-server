// JavaScript Document

    
 $(document).ready(function() {

   //  function getTestData2(player_data) {

         $.ajax({
             url: 'rest/data/collectionnames',
             type: 'GET',
             success: function(data) {
                 alert('data is ' + data);
             },
             error: function(xhr, status, error) {
                 var err = eval("(" + xhr.responseText + ")");
                 alert(err.Message);
             }
         });
  //   }




  
}); // end ready 


