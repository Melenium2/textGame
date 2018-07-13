$(document).ready(function(){
    
  $("#inpText").on('input', function(){
    $("#nextWord").html("");
    var that = this;
    var res = /[^а-яА-Я,ёЁ\-\.]/g.exec(that.value);
    if(that.value.match(res))
    {
        $("#warringText").css("display", "block");
    }
    else{
        $("#warringText").css("display", "none");
    }
    that.value = that.value.replace(res, '');
  });
      
});

$(document).on('contextmenu', function(e){
  return false;
});

$(document).on('selectstart', function(e){
  return false;
});

function textAreaChanger(selector)
{
  var selectedWord = selector.trim();
  var newSelectedWord = selectedWord+" ";
  var oldText = $("#textArea").html().replace("</span>", "").replace("<span>", "");
  var newText = oldText.replace(newSelectedWord, newSelectedWord + "</span>");
  $("#textArea").html("<span>"+newText);
}


