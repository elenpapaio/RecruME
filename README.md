# RecruME
It's a Spring Rest application that simulates operations of a recruitment company. There are repositories of applicants,
job offers, skills and the matchings that are accomplished through the app. There are service classes that 
manage the corresponding repositories into the services package, and controllers with the end points that call the
corresponding services. The app accomplishes registration, searching, updating and deleting functionalities for applicants,
job offers, skills and matches, by providing the json objects. Each time the http request returns the inserted or demanded json
to let us validate the proper function of the service. There is automating matching operation in case the applicant's 
skills fullfill completely the job offer's demands, and also manual matching. There are skills retrieve functionalities, 
providing most offered and requested skills. We can retrieve the finalized and not finalized matchings. The app imports to 
the database applicants,job offers and skills from excel and also exports to excel the finalized matches.
