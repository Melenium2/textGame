var interval;

function timer(varianta)
{    
  if(varianta == 'true')
  {
    interval = setInterval(function(){
      setTime(1);
    }, 1000);
  }
  else
  {
    clearInterval(interval);
  }
}

function setTime(seconds)
{
  var currentTime = $("#timer").html();
  var timeArr = currentTime.split(":")
  var h = timeArr[0];
  var m = timeArr[1];
  var s = timeArr[2];
  
  setAdditionalTime(h, m, s, seconds);
}

function setAdditionalTime(h, m, s, additionalSeconds)
{
  s = +s + additionalSeconds;
  console.log(s);
  if(s < 10)
  {
    s = '0' + s;
  }
  
  if(s >= 60)
  {
    m++;
    s -= 60;
    
    if(s == '0')
    {
      s = '0' + s;
    }
    
    if(m >= '03')
    {
      $("#timer").css('background-color', "#FA8072");
    }
    if(m < 10)
    {
      m = '0' + m;
    }
  }
  
  if(m == 60)
  {
    h++;
    m = '00';
    if(h < 10)
    {
      h = '0' + h;
    }
  }
  $("#timer").html(h+':'+m+':'+s);
}