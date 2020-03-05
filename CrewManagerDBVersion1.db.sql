BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `Users` (
	`MemberID`	INTEGER DEFAULT NULL UNIQUE,
	`Username`	TEXT DEFAULT NULL UNIQUE,
	`Password`	TEXT DEFAULT NULL UNIQUE,
	`Access`	INTEGER NOT NULL DEFAULT 0 CHECK(Access >= 0 AND Access <= 1)
);
CREATE TABLE IF NOT EXISTS `Tasks` (
	`TaskID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`JobID`	INTEGER NOT NULL,
	`MemberID`	INTEGER,
	`Description`	TEXT NOT NULL DEFAULT 'N/A',
	`IsCompleted`	INTEGER NOT NULL DEFAULT 0 CHECK(IsCompleted <= 0 AND IsCompleted <= 1)
);
CREATE TABLE IF NOT EXISTS `TaskList` (
	`TaskID`	INTEGER UNIQUE,
	`IsCompleted`	INTEGER NOT NULL DEFAULT 0 CHECK(IsCompleted >= 0 AND IsCompleted <= 1)
);
CREATE TABLE IF NOT EXISTS `NewFeedPosts` (
	`MemberID`	INTEGER NOT NULL,
	`TimePosted`	TEXT NOT NULL DEFAULT 'YYYY-MM-DD_HH:MM:SS',
	`Message`	TEXT DEFAULT NULL,
	FOREIGN KEY(`MemberID`) REFERENCES `Members`(`MemberID`)
);
CREATE TABLE IF NOT EXISTS `Members` (
	`MemberID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`FirstName`	TEXT NOT NULL DEFAULT 'N/A',
	`LastName`	TEXT NOT NULL DEFAULT 'N/A',
	`Phone`	TEXT DEFAULT NULL,
	`Age`	INTEGER DEFAULT NULL,
	`DateOfHire`	TEXT NOT NULL DEFAULT '00-00-0000',
	`ProfilePicture`	BLOB DEFAULT NULL
);
CREATE TABLE IF NOT EXISTS `Jobs` (
	`JobID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`JobName`	TEXT NOT NULL DEFAULT 'N/A',
	`StartDate`	TEXT NOT NULL DEFAULT '00-00-0000',
	`ExpectedJobDuration`	INTEGER NOT NULL DEFAULT 0,
	`ActualJobDuration`	INTEGER NOT NULL DEFAULT 0,
	`IsCompleted`	INTEGER NOT NULL DEFAULT 0 CHECK(IsCompleted >= 0 AND IsCompleted <= 1)
);
CREATE TABLE IF NOT EXISTS `JobMemberList` (
	`JobID`	INTEGER,
	`MemberID`	INTEGER,
	`Status`	INTEGER NOT NULL DEFAULT 0 CHECK(Status >= 0 AND Status <= 1),
	FOREIGN KEY(`JobID`) REFERENCES `Jobs`(`JobID`),
	FOREIGN KEY(`MemberID`) REFERENCES `Members`(`MemberID`)
);
COMMIT;
