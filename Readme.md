## How to execute the program

```bash
chmod a+x run.sh
./run.sh 
```

## Did i miss anything?

1. Yes, I didn't make UserClient as executable class, it would make little complicated to test it
2. In Spring application, It is generally preferable not-to-test the entry point, hence entry point is "com.modern.Main"

## List of tasks, how I did it, and time taken

1. Initially followed the guideline https://spring.io/guides/gs/reactive-rest-service/
2. Hit with netty-DNS error, and googled and updated dependency with classifier for Mac-M1 processor
3. Wasted time in Intercepting WebClient Junit Test and I tried to intercept the request and produce response (without mockserver)
   1. After 2hrs, I gave-it-up, and fallen back to time-tested mock web server
   2. I had other choice to use MockBean and expect the API, but generally I felt mocking API is very ugly and useless for my work
   3. Too much of mock-api eventually becomes useless, I am not able to reproduce production problems using mock-api heavy test-cases
4. 30 Minutes with mock-test  and mock-web-server
5. Add bells and whistles - 45 minutes
   1. run.sh
   2. Update Junit failure  message
   3. Test with wrong url
   4. Answer the question

## Generate more than 38 digit precision number

```avroidl
Use ".open FILENAME" to reopen on a persistent database.
D select * from read_parquet("user.parquet");
┌─────────┬─────────────────┬────────────────┬─────────────────────────┐
│  name   │ favorite_number │ favorite_color │       temperature       │
│ varchar │      int32      │    varchar     │     decimal(38,19)      │
├─────────┼─────────────────┼────────────────┼─────────────────────────┤
│ John    │              30 │ green          │ 123.4560000000000000000 │
│ Jane    │              28 │ blue           │   0.0100000000000000000 │
└─────────┴─────────────────┴────────────────┴─────────────────────────┘
D select * from read_parquet("user.parquet");
Invalid Input Error: Invalid decimal encoding in Parquet file
```