public class Controller {
    Model model;
    View view;
    public Controller(Model m, View v){
        model = m;
        view = v;
        //System.out.println("About to hit initView");
        initView();

    }
    public void initView(){
        // Set initial data to View, using Models get Methods
        //System.out.println("In initView");
        view.setLocationList(model.getDatabaseStr());
    }

    public void initController(){
        // Call
        //System.out.println("In initController");
        view.getSubmitButton().addActionListener(e -> submit());
    }

    private void submit(){
        // Update the Model's Data from the Data Entry Panel Upon Submit Button Click
        System.out.println("Submit has been called from within the Controller");
        System.out.println("Clearing Old Trip Data");
        model.clearPreviousData();
        view.clearTabs();
        model.setSelectedStartString(view.getSelectedStart());
        model.setSelectedListString(view.getSelectedList());
        model.setWeatherData();
        model.setCruiseAltitude(view.getCrusingAltitude());
        model.setCruisingSpeed(view.getCrusingSpeed());
        model.setDepartureTime(view.getDepartureTimeHour(), view.getDepartureTimeMin(), view.getDepartureTimeAmPm());
        model.setMaxDescentRate(view.getmaxDescentRate());
        model.setWeatherSource(view.getWeatherSource());

        // Call model's function to generate new trip's based on user entered locations
        model.generateTripList();
        // Get model's Trip List and pass to View
        view.setTrips(model.getTripList());
    }

    private void saveData(){

    }

    private void getData(){

    }


}
