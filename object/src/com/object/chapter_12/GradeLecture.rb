class Lecture
    def initialize(name, canceled, scores)
      @parent = Lecture.new(name, scores)
      @canceled = canceled
    end

    def stats(this)
      @parent.stats(this)
    end

    def getEvaluationMethod()
      "GRADE"
    end
end

