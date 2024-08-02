class Lecture
    def initialize(name, scores)
      @name = name
      @scores = scores
    end

    def stats(this)
      "Name: #{@name}, Evaluation Method: #{this.getEvaluationMethod()}"
    end

    def getEvaluationMethod()
      "Pass or Fail"
    end
end