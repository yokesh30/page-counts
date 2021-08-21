Requirements
---
1. Select some combination of database/processing platforms. We want the decisions here to be entirely up to you. 
The most important aspect here is that you can explain the thought process behind your decisions. 

## Processing technology
- The premise is to basically load the data from Wikipedia and store it on our dataset so that we can analyze the data and come up with the results. For processing the data, we would need 
some kind of job that will basically take the data in and transform the data into our dataset. Apache Spark is a classic use case for processing large scale data in real time and is open source
as well. Apache Spark can basically run on Hadoop clusters, and can basically integrate with other technologies in Hadoop cluster like Apache hive, Hbase etc. And also, if we have another job
that would basically depend on the output the previous job, we could leverage Apache Kafka and the donwstream jobs can basically consume data from the Kafka and do their processing.

## Database technology
In terms of the database selection, ideally we could use CAP theorem (Consistency, Availability and Partition tolerant). CAP theorem states that a database could ideally achieve any 2 of them
and not all 3. An example that basically satisfies Availability and Partition tolerant is Apache Cassandra database, similarly Consistency, Availability is satisfied by SQL databases.
For our case, I'll probably choose Apache Cassandra database as it is distributed, columnar database, highly available and can be partitioned across multiple nodes. 
It also guarantees eventual consistency.

But for this sample, I'm using MySql. To setup MySql, have docker installed and run the below docker command.

## Setting up the database
docker-compose up -d page-counts-db

2. Create and run an importation command or procedure for loading the wikipedia data set into some database for later analysis. 
Bonus points if you design a workflow that can be run not just for the aforementioned hour of page views, but for any arbitrary hour.

## Pseudo command for running the job
If we take a look at the wikipedia url (http://dumps.wikimedia.org/other/pagecounts-raw), it has several links for years, and within each year, we have several links for months,
and then days with the hours view link which is a compressed directory with the page view data by language. Before we process this data, we would need to have another job, which
basically crawls the site and downloads the compressed files and stores it in a distributed file system like HDFS or GFS etc, and also stores the metadata in a database. 
Once we have the metadata setup in our database, for example, the table could have columns like (id, name, file_location, created_at, processed_at, is_running, is_complete), 
the spark job to basically process the data could fetch the records from the table which are not processed yet by looking at the is_running column and then look
for the file in the HDFS from the file_location column and then process the data and basically store it in the Apache Cassandra database. Before we store the data, we have to aggregate the
data by the language and store it in the table. The table could be page_counts table and the columns will be (id, language, page_name, count, start_time, end_time, created_at)

For setting up the hadoop on my local machine, we can use hdp docker commands (docker pull hortonworks/sandbox-hdp-standalone). This docker image will basically setup hadoop in
our local machine and we should be able to start local development process. And then basically develop spark jobs and use spark submit jobs to basically load the data int our database.

I have added a sample gradle task that can basically submit a spark job for references.
 
I was trying to setup the local hdp and build a sample job, but had some hard time doing it. So, I thought I'll explain the thought process and then share some code on how it would look like.

3. Develop a small application in your favorite language to read from your data store and calculate the 10 most popular pages by language. 
Bonus points for coming up with other useful or interesting queries/applications of the dataset.

The application code basically mocks the data on startup of the spring boot app. I have added flyway migration scripts which will basically setup the database and the data.
The LanguagesController basically ahs two methods which getTopLanguagesByLanguage and getTopLanguagesByLanguageAsCSV. The first method will basically return the top 10 languages as a json.
The second method getTopLanguagesByLanguageAsCSV will return the output as a csv.

## Running the app
In order to run the app, run the below command,
```
./gradlew bootRun
```

4. Your submission should contain a README that contains step-by-step instructions on how to set up and run your code as well as any insight into your thought process that you'd like to share.  These are both very important!

5. Please try to design the system so that we can run it on our workstations. This might sound in conflict with designing for parallel scalability, but there are ways to achieve both. 
Many good open-source solutions for distributed processing support a local runner mode.

6. Please include automated unit tests and instructions on how to run them.
## Testing the app

In order to test the app, run the below command,
```
./gradlew test
```

7. Your code should be able to output the results to one or more human readable data files (I.E CSV, TSV, JSON, etc.)

## Getting the top 10 languages
To get the top languages by csv, open the following url,
http://localhost:7000/languages/top10/csv

To get the top languages as json, open the following url,
http://localhost:7000/languages/top10