jQuery(function(){

   var showDeprecatedChecbox = jQuery("#showDeprecated");

   showDeprecatedChecbox.change(function() {
      toggleShowDeprecated();
   });

   function toggleShowDeprecated() {
      jQuery('body').toggleClass('showDeprecated', showDeprecatedChecbox.is(':checked'));
   }

   toggleShowDeprecated();
});