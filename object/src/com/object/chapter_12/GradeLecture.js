function GradeLecture(name, canceled, scores){
    Lecture.call(this, name, scores)
    this.canceled = canceled;
}

GradeLecture.prototype = new Lecture()

GradeLecture.prototype.constructor = GradeLecture;

GradeLecture.prototype.getEvaluationMethod = function (){
    return "GRADE"
}