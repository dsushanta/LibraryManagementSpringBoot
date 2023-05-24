-- MySQL dump 10.13  Distrib 8.0.31, for macos13.0 (x86_64)
--
-- Host: localhost    Database: LIBRARY_MANAGEMENT
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (55);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LI_BOOK`
--

DROP TABLE IF EXISTS `LI_BOOK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LI_BOOK` (
  `BookId` int NOT NULL,
  `Author` text NOT NULL,
  `Available` tinyint(1) NOT NULL DEFAULT '1',
  `Description` text NOT NULL,
  `Genre` varchar(255) NOT NULL,
  `Likes` int NOT NULL DEFAULT '0',
  `Title` text NOT NULL,
  PRIMARY KEY (`BookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LI_BOOK`
--

LOCK TABLES `LI_BOOK` WRITE;
/*!40000 ALTER TABLE `LI_BOOK` DISABLE KEYS */;
INSERT INTO `LI_BOOK` VALUES (1,'Jim Corbett',1,'Man-Eaters of Kumaon is a 1944 book written by hunter-naturalist Jim Corbett','Adventure',1,'Man-Eaters of Kumaon'),(2,'Jerome Klapka Jerome',1,'Three Men in a Boat (To Say Nothing of the Dog),[Note 1] published in 1889,[1] is a humorous account by English writer Jerome K. Jerome of a two-week boating holiday on the Thames from Kingston upon Thames to Oxford and back to Kingston. The book was initially intended to be a serious travel guide,[2] with accounts of local history along the route, but the humorous elements took over to the point where the serious and somewhat sentimental passages seem a distraction to the comic novel. One of the most praised things about Three Men in a Boat is how undated it appears to modern readers – the jokes have been praised as fresh and witty','Comedy',2,'Three Men in a Boat'),(3,'John Kennedy Toole',0,'A Confederacy of Dunces is a picaresque novel by American novelist John Kennedy Toole which reached publication in 1980, eleven years after Toole\'s suicide','Comedy',3,'A Confederacy of Dunces'),(4,'Rudyard Kipling',0,'The Jungle Book (1894) is a collection of stories by the English author Rudyard Kipling. Most of the characters are animals such as Shere Khan the tiger and Baloo the bear, though a principal character is the boy or man-cub Mowgli, who is raised in the jungle by wolves. The stories are set in a forest in India; one place mentioned repeatedly is Seonee (Seoni), in the central state of Madhya Pradesh','Adventure',4,'The Jungle Book'),(5,'Dan Brown',1,'The Da Vinci Code is a 2003 mystery thriller novel by Dan Brown. It is Brown\'s second novel to include the character Robert Langdon: the first was his 2000 novel Angels & Demons. The Da Vinci Code follows symbologist Robert Langdon and cryptologist Sophie Neveu after a murder in the Louvre Museum in Paris causes them to become involved in a battle between the Priory of Sion and Opus Dei over the possibility of Jesus Christ having been a companion to Mary Magdalene','Adventure',5,'The Da Vinci Code'),(6,'Amish Tripathi',1,'The Immortals of Meluha is the first novel of the Shiva trilogy series by Amish Tripathi. The story is set in the land of Meluha and starts with the arrival of the Shiva. The Meluhans believe that Shiva is their fabled saviour Neelkanth. Shiva decides to help the Meluhans in their war against the Chandravanshis, who had joined forces with a cursed Nagas; however, during his journey and the fight that ensues, Shiva learns how his choices actually reflect who he aspires to be and how they lead to dire consequences','Fiction',6,'The Immortals of Meluha'),(7,'Amish Tripathi',1,'The Secret of the Nagas is the second novel of the Shiva trilogy series by the Indian author Amish Tripathi. The story takes place in the imaginary land of Meluha and narrates how the inhabitants of that land are saved from their wars by a nomad named Shiva. It begins from where its predecessor, The Immortals of Meluha, left off, with Shiva trying to save Sati from the invading Naga. Later Shiva takes his troop of soldiers and travels far east to the land of Branga, where he wishes to find a clue to reach the Naga people. Shiva also learns that Sati\'s first child is still alive, as well as her twin sister. His journey ultimately leads him to the Naga capital of Panchavati, where he finds a surprise waiting for him','Fiction',7,'The Secret of the Nagas'),(8,'Amish Tripathi',1,'The Oath of the Vayuputras is a 2013 novel by Indian author Amish Tripathi and the final book in his Shiva trilogy. The book was released on 27 February 2013, through Westland Press and completes the mythical story about an imaginary land Meluha and how its inhabitants were saved by a nomad named Shiva. Starting from where the previous installment left off, Shiva discovers that Somras is the true evil in The Oath of the Vayuputras. Shiva then declares a holy war on those who seek to continue to use it, mainly the Emperors Daksha and Dilipa, who are being controlled by the sage Bhrigu. The battle rages on and Shiva travels to the land of Pariha to consult with Vayuputras, a legendary tribe. By the time he returns, the war has ended with Sati, his wife, being murdered. An enraged Shiva destroys the capital of Meluha and Somras is wiped out of history. The story concludes with Shiva and his associates being popularized as Gods for their deeds and accomplishments','Fiction',8,'The Oath of the Vayuputras'),(9,'Rabindranath Tagore',1,'‘Chitrangada’, a dance drama, originally composed by Nobel laureate Gurudeb Rabindranath Tagore in 1892, is based on the love life of Manipur’s princess Chitrangada and Arjun, the third Pandava of the epic Mahabharata, and it documents the emotional journey of Chitrangada as she is awakened by her irresistible passion for the love of her life, Arjun. According to Mahabharata, Arjuna, the brave warrior travelled the length and breadth of India during his term of exile, and it was during his wanderings that he had a brief sojourn in ancient Manipur, a mystic kingdom renowned for its natural beauty. There, he met Chitrangada, the daughter of the king of Manipur and eventually married him on the preconditioned premise that he would never take away either Chitrangada or their children from her maiden kingdom of Manipur. The couple later had a son named Babruvahana','Drama',9,'Chitrangada'),(10,'William Shakespeare',1,'Macbeth (full title The Tragedy of Macbeth) is a tragedy by William Shakespeare; it is thought to have been first performed in 1606. It dramatises the damaging physical and psychological effects of political ambition on those who seek power for its own sake. Of all the plays that Shakespeare wrote during the reign of James I, who was patron of Shakespeare\'s acting company, Macbeth most clearly reflects the playwright\'s relationship with his sovereign. It was first published in the Folio of 1623, possibly from a prompt book, and is Shakespeare\'s shortest tragedy.','Drama',8,'Macbeth'),(11,'Shirley Jackson',1,'The Haunting of Hill House is a 1959 gothic horror novel by American author Shirley Jackson. A finalist for the National Book Award and considered one of the best literary ghost stories published during the 20th century,it has been made into two feature films and a play, and is the basis of a Netflix series. Jackson\'s novel relies on terror rather than horror to elicit emotion in the reader, using complex relationships between the mysterious events in the house and the characters’ psyches','Horror',7,'The Haunting of Hill House'),(12,'Bram Stoker',1,'Dracula is an 1897 Gothic horror novel by Irish author Bram Stoker. It introduced the character of Count Dracula, and established many conventions of subsequent vampire fantasy. The novel tells the story of Dracula\'s attempt to move from Transylvania to England so that he may find new blood and spread the undead curse, and of the battle between Dracula and a small group of men and a woman led by Professor Abraham Van Helsing.','Horror',6,'Dracula'),(14,'J. R. R. Tolkien',0,'The Lord of the Rings is an epic high fantasy novel written by English author and scholar J. R. R. Tolkien. The story began as a sequel to Tolkien\'s 1937 fantasy novel The Hobbit, but eventually developed into a much larger work. Written in stages between 1937 and 1949, The Lord of the Rings is one of the best-selling novels ever written, with over 150 million copies sold.The title of the novel refers to the story\'s main antagonist, the Dark Lord Sauron,[a] who had in an earlier age created the One Ring to rule the other Rings of Power as the ultimate weapon in his campaign to conquer and rule all of Middle-earth. From quiet beginnings in the Shire, a hobbit land not unlike the English countryside, the story ranges across Middle-earth, following the course of the War of the Ring through the eyes of its characters, most notably the hobbits Frodo Baggins, Sam, Merry and Pippin.','Fantasy',5,'The Lord of the Rings'),(15,'R M Lala',0,'An exhaustive and unforgettable portrait of India\'s greatest and most respected industrialist. Written with J.R.D. Tata\'s co-operation, this superb biography tells the J.R.D. story from his birth to 1993, the year in which he died in Switzerland. The book is divided into four parts: Part I deals with the early years, from J.R.D\'s birth in France in 1904 to his accession to the chairmanship of Tatas, India\'s largest industrial conglomerate, at the age of thirty-four; Part II looks at his forty-six years in Indian aviation (the lasting passion of J.R.D\'s life) which led to the initiation of the Indian aviation industry and its development into one of India\'s success stories; Part III illuminates his half-century-long stint as the outstanding personality of Indian industry; and Part IV unearths hitherto unknown details about the private man and the public figure, including glimpses of his long friendships with such people as Jawaharlal Nehru, Mahatma Gandhi, Indira Gandhi and his association with celebrities in India and abroad','Biography',4,'Beyond the last blue mountain'),(16,'Adolf Hitler',0,'Mein Kampf (German: [ma??n kampf], My Struggle or My Fight) is a 1925 autobiographical manifesto by Nazi Party leader Adolf Hitler. The work describes the process by which Hitler became antisemitic and outlines his political ideology and future plans for Germany. Volume 1 of Mein Kampf was published in 1925 and Volume 2 in 1926. The book was edited firstly by Emil Maurice, then by Hitler\'s deputy Rudolf Hess.Hitler began Mein Kampf while imprisoned for what he considered to be \'political crimes\' following his failed Putsch in Munich in November 1923. Although Hitler received many visitors initially, he soon devoted himself entirely to the book. As he continued, Hitler realized that it would have to be a two-volume work, with the first volume scheduled for release in early 1925. The governor of Landsberg noted at the time that \'he [Hitler] hopes the book will run into many editions, thus enabling him to fulfill his financial obligations and to defray the expenses incurred at the time of his trial. After slow initial sales, the book was a bestseller in Germany after Hitler\'s rise to power in 1933. After Hitler\'s death, copyright of Mein Kampf passed to the state government of Bavaria, which refused to allow any copying or printing of the book in Germany. In 2016, following the expiration of the copyright held by the Bavarian state government, Mein Kampf was republished in Germany for the first time since 1945, which prompted public debate and divided reactions from Jewish groups','Biography',3,'Mein Kampf'),(17,'A Game of Thrones',1,'A Game of Thrones is the first novel in A Song of Ice and Fire, a series of fantasy novels by the American author George R. R. Martin. It was first published on August 1, 1996. The novel won the 1997 Locus Award and was nominated for both the 1997 Nebula Award and the 1997 World Fantasy Award. The novella Blood of the Dragon, comprising the Daenerys Targaryen chapters from the novel, won the 1997 Hugo Award for Best Novella.','Fantasy',2,'A Game of Thrones'),(18,'Napoleon Hill',1,'Think and Grow Rich is probably the most important financial book you can ever hope to read. Inspiring generations of readers since the time it was first published in 1937, it has been used by millions of business leaders around the world to create a concrete plan for success that, when followed, never fails.','Self-Help',1,'Think & Grow Rich'),(19,'Joseph Murthy',1,'This book can bring to your notice the innate power that the sub-conscious holds. We have some traits which seem like habits, but in reality these are those traits which are directly controlled by the sub-conscious mind, vis-à-vis your habits or your routine can be changed if you can control and direct your sub-conscious mind positively. To be able to control this \'mind power\' and use it to improve the quality of your life is no walk in the park. This is where this book acts as a guide and allows you to decipher the depths of the sub-conscious.','Self-Help',2,'Power of your Subconscious Mind'),(32,'qqqqqqqqqqqqqqqq',0,'qqqqqqqqqqqqqqq','xyzxyzxyzxyz',3,'qqqqqqqqqqqqqq'),(33,'pppp',1,'pppp','pppp',4,'pppp'),(34,'test-author-1',1,'test test test','test',5,'test-book-1'),(35,'test-author-2',1,'test test test','test',6,'test-book-2'),(36,'test-author-3',1,'test test test','test',7,'test-book-3'),(37,'Bram Stoker',1,'Piku 1245','Horror',8,'Piku');
/*!40000 ALTER TABLE `LI_BOOK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LI_BOOK_COPY`
--

DROP TABLE IF EXISTS `LI_BOOK_COPY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LI_BOOK_COPY` (
  `CopyId` int NOT NULL,
  `IsIssued` tinyint(1) NOT NULL DEFAULT '1',
  `BookId` int DEFAULT NULL,
  PRIMARY KEY (`CopyId`),
  KEY `FK6u8w89ui61fi397s2ecxriyb2` (`BookId`),
  CONSTRAINT `FK6u8w89ui61fi397s2ecxriyb2` FOREIGN KEY (`BookId`) REFERENCES `LI_BOOK` (`BookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LI_BOOK_COPY`
--

LOCK TABLES `LI_BOOK_COPY` WRITE;
/*!40000 ALTER TABLE `LI_BOOK_COPY` DISABLE KEYS */;
INSERT INTO `LI_BOOK_COPY` VALUES (3,0,2),(4,1,3),(5,1,4),(6,0,5),(7,0,6),(8,0,7),(9,0,8),(10,0,9),(11,0,10),(12,0,11),(15,1,14),(18,1,15),(19,1,16),(20,1,17),(21,1,17),(22,0,18),(23,1,17),(24,0,19),(38,1,4),(39,1,32),(40,1,32),(41,1,33),(42,0,33),(43,0,33);
/*!40000 ALTER TABLE `LI_BOOK_COPY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LI_BOOK_LIFE_CYCLE`
--

DROP TABLE IF EXISTS `LI_BOOK_LIFE_CYCLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LI_BOOK_LIFE_CYCLE` (
  `BookIssueId` int NOT NULL,
  `ActualReturnDate` date DEFAULT NULL,
  `ExpectedReturnDate` date NOT NULL,
  `Fine` int DEFAULT '0',
  `FineCleared` tinyint(1) DEFAULT '1',
  `IsRenewed` tinyint(1) NOT NULL DEFAULT '0',
  `IsReturned` tinyint(1) NOT NULL DEFAULT '0',
  `IssueDate` date NOT NULL,
  `RenewDate` date DEFAULT NULL,
  `BookId` int DEFAULT NULL,
  `CopyId` int DEFAULT NULL,
  `UserName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`BookIssueId`),
  KEY `FK4a8jo1aieu3fxsd34gfdc3qw6` (`BookId`),
  KEY `FKioj761f3bkrhb2j8kr466ouye` (`CopyId`),
  KEY `FKgkgfcgm9b8r1bso2cqwwdalit` (`UserName`),
  CONSTRAINT `FK4a8jo1aieu3fxsd34gfdc3qw6` FOREIGN KEY (`BookId`) REFERENCES `LI_BOOK` (`BookId`),
  CONSTRAINT `FKgkgfcgm9b8r1bso2cqwwdalit` FOREIGN KEY (`UserName`) REFERENCES `LI_USER` (`UserName`),
  CONSTRAINT `FKioj761f3bkrhb2j8kr466ouye` FOREIGN KEY (`CopyId`) REFERENCES `LI_BOOK_COPY` (`CopyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LI_BOOK_LIFE_CYCLE`
--

LOCK TABLES `LI_BOOK_LIFE_CYCLE` WRITE;
/*!40000 ALTER TABLE `LI_BOOK_LIFE_CYCLE` DISABLE KEYS */;
INSERT INTO `LI_BOOK_LIFE_CYCLE` VALUES (1,'2019-08-13','2019-08-08',200,1,1,1,'2019-08-13','2019-08-13',17,20,'chandler'),(2,'2019-08-13','2019-08-08',100,1,0,1,'2019-08-13',NULL,16,19,'chandler'),(3,'2023-02-17','2023-02-12',51560,0,1,1,'2019-08-13','2023-02-17',17,20,'ross'),(4,'2019-08-14','2019-08-08',220,1,1,1,'2019-08-13','2019-08-13',2,3,'chandler'),(5,'2019-08-16','2019-08-09',140,1,0,1,'2019-08-14',NULL,10,11,'sushantaD'),(6,'2019-08-16','2019-08-10',240,1,1,1,'2019-08-14','2019-08-15',19,24,'sushantaD'),(7,'2019-08-16','2019-08-10',220,1,1,1,'2019-08-15','2019-08-15',11,12,'sushantaD'),(8,'2019-08-16','2019-08-11',220,1,1,1,'2019-08-15','2019-08-16',15,18,'sushantaD'),(9,'2019-08-16','2019-08-11',220,1,1,1,'2019-08-15','2019-08-16',18,22,'sushantaD'),(10,'2019-08-16','2019-08-10',220,1,1,1,'2019-08-15','2019-08-15',7,8,'chandler'),(11,'2019-08-16','2019-08-10',120,1,0,1,'2019-08-15',NULL,2,3,'udrocks'),(12,'2019-08-16','2019-08-11',220,1,1,1,'2019-08-15','2019-08-16',14,15,'udrocks'),(13,NULL,'2019-08-10',0,1,0,0,'2019-08-15',NULL,4,5,'udrocks'),(14,NULL,'2019-08-11',120,1,1,0,'2019-08-15','2019-08-16',16,19,'udrocks'),(15,'2019-08-16','2019-08-10',220,1,1,1,'2019-08-15','2019-08-15',17,21,'chandler'),(16,'2019-08-16','2019-08-11',220,1,1,1,'2019-08-15','2019-08-16',17,23,'chandler'),(17,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,15,18,'sushantaD'),(18,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,6,7,'sushantaD'),(19,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,3,4,'sushantaD'),(20,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,3,4,'sushantaD'),(21,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,19,24,'sushantaD'),(22,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,18,22,'sushantaD'),(23,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,3,4,'sushantaD'),(24,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,5,6,'sushantaD'),(25,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,18,22,'sushantaD'),(26,'2019-08-17','2019-08-11',220,1,1,1,'2019-08-16','2019-08-16',19,24,'sushantaD'),(27,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,3,4,'sushantaD'),(28,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,17,21,'sushantaD'),(29,'2019-08-16','2019-08-11',100,1,0,1,'2019-08-16',NULL,5,6,'sushantaD'),(30,'2019-08-18','2019-08-11',140,1,0,1,'2019-08-16',NULL,18,22,'sushantaD'),(32,'2019-08-17','2019-08-11',140,1,1,1,'2019-08-16','2019-08-16',15,18,'sushantaD'),(33,'2019-08-17','2019-08-15',50,1,1,1,'2019-08-16','2019-08-16',5,6,'sushantaD'),(34,'2019-08-16','2019-08-15',20,1,0,1,'2019-08-16',NULL,3,4,'sushantaD'),(37,NULL,'2019-08-11',0,1,0,0,'2019-08-16',NULL,4,38,'chandler'),(38,NULL,'2019-08-11',0,1,0,0,'2019-08-16',NULL,32,39,'chandler'),(39,'2019-08-17','2019-08-12',100,1,0,1,'2019-08-17',NULL,9,10,'chandler'),(40,NULL,'2019-08-12',0,1,0,0,'2019-08-17',NULL,33,41,'chandler'),(41,'2019-08-17','2019-08-12',100,1,0,1,'2019-08-17',NULL,33,42,'chandler'),(42,'2019-08-17','2019-08-12',100,1,0,1,'2019-08-17',NULL,33,43,'udrocks'),(44,'2019-08-19','2019-08-13',240,0,1,1,'2019-08-17','2019-08-18',6,7,'sushantaD'),(45,'2019-08-18','2019-08-12',120,1,0,1,'2019-08-17',NULL,5,6,'sushantaD'),(47,NULL,'2023-02-12',1,1,0,0,'2023-02-17',NULL,17,20,'joey'),(48,NULL,'2023-02-12',2,1,0,0,'2023-02-17',NULL,17,20,'joey'),(49,NULL,'2023-02-12',3,1,0,0,'2023-02-17',NULL,17,20,'joey'),(50,NULL,'2023-02-24',4,1,0,0,'2023-02-17',NULL,17,20,'joey'),(51,NULL,'2023-02-24',5,1,0,0,'2023-02-17',NULL,17,20,'joey'),(54,NULL,'2023-02-24',0,0,0,0,'2023-02-17',NULL,17,20,'joey');
/*!40000 ALTER TABLE `LI_BOOK_LIFE_CYCLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LI_CONFIG`
--

DROP TABLE IF EXISTS `LI_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LI_CONFIG` (
  `Id` int NOT NULL,
  `FinePerDay` int DEFAULT '0',
  `NoOfBooksPerUser` int DEFAULT '0',
  `NoOfDaysAUserCanKeepABook` int DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LI_CONFIG`
--

LOCK TABLES `LI_CONFIG` WRITE;
/*!40000 ALTER TABLE `LI_CONFIG` DISABLE KEYS */;
INSERT INTO `LI_CONFIG` VALUES (1,20,5,7);
/*!40000 ALTER TABLE `LI_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LI_USER`
--

DROP TABLE IF EXISTS `LI_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LI_USER` (
  `UserName` varchar(255) NOT NULL,
  `BookCount` int DEFAULT '0',
  `Email` varchar(255) NOT NULL,
  `FavGenre` varchar(255) DEFAULT NULL,
  `Fine` int DEFAULT '0',
  `FirstName` text NOT NULL,
  `LastName` text NOT NULL,
  PRIMARY KEY (`UserName`),
  UNIQUE KEY `Unique_Constraint_Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LI_USER`
--

LOCK TABLES `LI_USER` WRITE;
/*!40000 ALTER TABLE `LI_USER` DISABLE KEYS */;
INSERT INTO `LI_USER` VALUES ('admin',0,'library.admin@library.com',NULL,0,'Library','Admin'),('anirband',2,'anirban@gmail.com','Fiction',0,'Anirban','Das'),('chandler',5,'chandler.bing@library.com','Comedy',0,'Chandler','Bing'),('ironman',0,'ironman@gmail.com','Adventure',0,'Tony','Stark'),('joey',3,'joey.tribbiani@library.com','Fiction',0,'Joey','Tribbiani'),('monica',0,'monica.geller@library.com','Drama',0,'Monica','Geller'),('phoebe',0,'phoebe.buffay@library.com','Horror',11140,'Phoebe','Buffay'),('rachel',0,'rachel.green@library.com','Fantasy',0,'Rachel','Green'),('ross',0,'ross.geller@library.com','Biography',51560,'Ross','Geller'),('sushantaD',1,'sd@gmail.com','Horror',720,'Sushanta','Dasgupta'),('udrocks',4,'ud@gmail.com','Fiction',0,'Urmisha','Das');
/*!40000 ALTER TABLE `LI_USER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-18 22:59:33
