package chapter_10.gumballstate;

public class GumballMachine {
 
	State soldOutState;
	State noQuarterState;
	State hasQuarterState;
	State soldState;

	State state;
	int count = 0;

	public GumballMachine(int numberGumballs) {
		soldOutState = new SoldOutState(this);
		noQuarterState = new NoQuarterState(this);
		hasQuarterState = new HasQuarterState(this);
		soldState = new SoldState(this);

		this.count = numberGumballs;

		if(numberGumballs > 0){
			state = noQuarterState;
		}
		else{
			state = soldOutState;
		}
	}

	public void insertQuarter(){
		state.insertQuarter();
	}

	public void ejectQuarter(){
		state.ejectQuarter();
	}

	public void turnCrank(){
		state.turnCrank();
		state.dispense();
	}

	public State getSoldOutState() {
		return soldOutState;
	}

	public State getNoQuarterState() {
		return noQuarterState;
	}

	public State getHasQuarterState() {
		return hasQuarterState;
	}

	public State getSoldState() {
		return soldState;
	}

	public int getCount() {
		return count;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void releaseBall(){
		System.out.println("알맹이를 내보내고 있습니다.");
		if(count > 0){
			count = count - 1;
		}
	}
}


