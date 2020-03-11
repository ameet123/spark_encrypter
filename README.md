## Spark Encryption
### Objective:
Encrypt a spark `DataFrame` **before** writing to disk/file.

### Background:
In certain scenarios, it may not be allowed to persist the dataframe to disk even temporarily. This may be prohibited
 due to some regulations. 
 
### Approach:
Currently, no option exists on `DataFrameWriter` to encrypt the data frame being generated. However, there is an
 option to compress the dataframe before writing it to a file on disk. This is provided by the option, `codec`.
Spark allows using fully qualified class name of a class implementing, `org.apache.hadoop.io.compress.CompressionCodec`.

We can use a class which implements the above interface and provide the encryption functionality. The main method
 that this interface demands is as follows,
 ```java
CompressionOutputStream createOutputStream(OutputStream out)
```
`CompressionOutputStream` extends standard java `OutputStream`. 

Based on this, the high level approach is as follows,

+ Create a class which implements `CompressionCodec`
+ provide logic in method `createOutputStream`
+ Provide an encrypted stream as a class of `CompressionOutputStream`

![flow](image/flow.jpg)

The command to execute the encryption is summarized as follows,

```scala
df.write.format("<file fmt>").option("codec", "org.ameet.codec.EncryptionCodec").save("<file path>")
```