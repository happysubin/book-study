function Lecture(name, scores){
    this.name = name;
    this.scores = scores;
}

Lecture.prototype.stats = function(){
    return "Name: " + this.name + ", Evaluation Method: " + this.getEvaluationMethod();
}

Lecture.prototype.getEvaluationMethod = function (){
    return "Pass or Fail"
}