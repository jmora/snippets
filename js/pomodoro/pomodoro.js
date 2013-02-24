// JavaScript Document

//https://github.com/12meses12katas/Diciembre-PomodoroKata

$('document').ready(function(){
  var PomodoroGlobals = {
    truncate : function (x){
      return x > 0? Math.floor(x): Math.ceil(x);
    },
    
    Pomodoro : function (args){

      if (typeof(args) != 'object' || !args.hasOwnProperty('length') || args.length != 2){
        args = [25, 0];
      }
      this.end = new Date((new Date()).getTime() + (args[0] * 60 + args[1])  * 1000);
      this.count = -1;
      this.started = true;
      
      this.stop = function(){
        this.started = false;
      };
      
      this.updateVals = function(){
        if (this.started){
          if ((new Date()) > this.end){
            this.started = false;
            $('input[name=remainingTime]').val("0:00");
          }
          else {
            var d = new Date(this.end - new Date());
            var seconds = ("0" + d.getSeconds()).slice(-2);
            $('input[name=remainingTime]').val(d.getMinutes() + ':' + seconds);
          }
          var that = this;
          setTimeout(function(){that.updateVals();}, 950);
        }
      };
      
      this.interruption = function(){
        if (this.started)
          this.count += 1;
        $('input[name=count]').val(this.count);
      };
      
      this.updateVals();
      this.interruption();
    }
  };
	
  $('#start').click(function(){
    var args = (($('input[name=timeStart]').val()).split(':')).map(function(value, index){
      return parseInt(value,10);
    });
    if (typeof(PomodoroGlobals.p) !== "undefined")
      PomodoroGlobals.p.stop();
    PomodoroGlobals.p = new PomodoroGlobals.Pomodoro(args);
  });
  
  $('#count').click(function(){
    PomodoroGlobals.p.interruption();
  });
  
});
