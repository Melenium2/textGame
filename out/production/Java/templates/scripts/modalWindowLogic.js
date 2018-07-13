function invokeModal(mess, countNow, countAll)
{
  $("#blockPauseText").html("Next word is: " + "<b>" + mess + "</b>" + ", "
                            + "You type " + "<b>" + countNow + " / " + countAll + "</b>" + " words");
  $("#modalTrigger")[0].click();
}

var answerForQuestion = "";

function startQuestionModal(number, question, mainAnswer,answer1, answer2, answer3, answer4)
{
  $("#titleQuestion").text("Question#" + number);
  $("#blockQuestionText").html(question);
  $("#answer1").text(answer1);
  $("#answer2").text(answer2);
  $("#answer3").text(answer3);
  $("#answer4").text(answer4);
  answerForQuestion = mainAnswer;
  $("#inpText").attr('disabled', 'disabled');
  

  $("#modalTriggerQuestion")[0].click();
}

$(".btnAnswer").each(function(key, element){
  $(element).click(function(){
    isAnswerTrue($(element).text());
    /*console.log(element.classList);
    $(element).removeClass('btn-q');
    console.log(element.classList);
    $(element).addClass('btn-q-true');
    console.log(element.classList);*/
  });
});

function isAnswerTrue(answer)
{
  if(answer === answerForQuestion){
    changeButtonColor(getIndexOfButtonByName(answer));
    $("#inpText").val("is answer true");
    setTimeout(function(){
      $("#btnCloseQ")[0].click();
      clearModal();
    }, 1500);
  }
  else{
    changeButtonColor(getIndexOfButtonByName(answerForQuestion));
    $("#inpText").val("is answer false");
    setTime(30);
    setTimeout(function(){
      $("#btnCloseQ")[0].click();
      clearModal();
    }, 1500)
  }
}

function getIndexOfButtonByName(answer)
{
  var index;
  for(var i=0; i<4; i++){
    if(document.getElementsByClassName('btnAnswer')[i].textContent === answer){
      index = i;
    }
  }
  return ++index;
}

function changeButtonColor(btnName)
{
  $("#answer"+(btnName)).removeClass('btn-q');
  $("#answer"+(btnName)).addClass('btn-q-true');
  
  $("#answer1").prop('disabled', true);
  $("#answer2").prop('disabled', true);
  $("#answer3").prop('disabled', true);
  $("#answer4").prop('disabled', true);
}

function clearModal()
{
  $("#titleQuestion").text("");
  $("#blockQuestionText").html("");
  $("#inpText").removeAttr("disabled");
  
  if($("#answer"+getIndexOfButtonByName(answerForQuestion)).hasClass('btn-q-true')){
    $("#inpText").val("Сообщенме");
  }
  $("#answer"+getIndexOfButtonByName(answerForQuestion)).removeClass('btn-q-true');
  $("#answer"+getIndexOfButtonByName(answerForQuestion)).addClass('btn-q');
  for(var i=1 ;i < 5; i++) {

    $("#answer"+i).removeAttr("disabled");
    $("#answer"+i).text("");
  }

} 