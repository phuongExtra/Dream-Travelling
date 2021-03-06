USE [master]
GO
/****** Object:  Database [TravelDB]    Script Date: 06/22/2020 23:38:18 ******/
CREATE DATABASE [TravelDB] 
GO
ALTER DATABASE [TravelDB] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [TravelDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [TravelDB] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [TravelDB] SET ANSI_NULLS OFF
GO
ALTER DATABASE [TravelDB] SET ANSI_PADDING OFF
GO
ALTER DATABASE [TravelDB] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [TravelDB] SET ARITHABORT OFF
GO
ALTER DATABASE [TravelDB] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [TravelDB] SET AUTO_CREATE_STATISTICS ON
GO
ALTER DATABASE [TravelDB] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [TravelDB] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [TravelDB] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [TravelDB] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [TravelDB] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [TravelDB] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [TravelDB] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [TravelDB] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [TravelDB] SET  DISABLE_BROKER
GO
ALTER DATABASE [TravelDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [TravelDB] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [TravelDB] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [TravelDB] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [TravelDB] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [TravelDB] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [TravelDB] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [TravelDB] SET  READ_WRITE
GO
ALTER DATABASE [TravelDB] SET RECOVERY SIMPLE
GO
ALTER DATABASE [TravelDB] SET  MULTI_USER
GO
ALTER DATABASE [TravelDB] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [TravelDB] SET DB_CHAINING OFF
GO
USE [TravelDB]
GO
/****** Object:  Table [dbo].[tblStatus]    Script Date: 06/22/2020 23:38:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblStatus](
	[statusID] [int] NOT NULL,
	[status] [varchar](50) NULL,
 CONSTRAINT [PK_tblStatus] PRIMARY KEY CLUSTERED 
(
	[statusID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblStatus] ([statusID], [status]) VALUES (1, N'active')
INSERT [dbo].[tblStatus] ([statusID], [status]) VALUES (2, N'deactive')
INSERT [dbo].[tblStatus] ([statusID], [status]) VALUES (3, N'new')
/****** Object:  Table [dbo].[tblRoles]    Script Date: 06/22/2020 23:38:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRoles](
	[roleID] [int] NOT NULL,
	[role] [varchar](50) NULL,
 CONSTRAINT [PK_tblRoles] PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblRoles] ([roleID], [role]) VALUES (1, N'admin')
INSERT [dbo].[tblRoles] ([roleID], [role]) VALUES (2, N'user')
/****** Object:  Table [dbo].[tblDiscount]    Script Date: 06/22/2020 23:38:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblDiscount](
	[discountID] [varchar](50) NOT NULL,
	[discountCode] [varchar](50) NULL,
	[value] [int] NULL,
	[expireDate] [date] NULL,
 CONSTRAINT [PK_tblDiscount] PRIMARY KEY CLUSTERED 
(
	[discountID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblDiscount] ([discountID], [discountCode], [value], [expireDate]) VALUES (N'D1', N'LESS20', 20, CAST(0x46410B00 AS Date))
INSERT [dbo].[tblDiscount] ([discountID], [discountCode], [value], [expireDate]) VALUES (N'D2', N'LESS30', 30, CAST(0x66410B00 AS Date))
/****** Object:  Table [dbo].[tblTours]    Script Date: 06/22/2020 23:38:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblTours](
	[tourID] [varchar](50) NOT NULL,
	[tourName] [varchar](50) NULL,
	[fromDate] [date] NULL,
	[toDate] [date] NULL,
	[img] [varchar](50) NULL,
	[price] [int] NULL,
	[quota] [int] NULL,
	[dateImport] [date] NULL,
	[destination] [varchar](50) NULL,
	[statusID] [int] NULL,
 CONSTRAINT [PK_tblTours] PRIMARY KEY CLUSTERED 
(
	[tourID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblTours] ([tourID], [tourName], [fromDate], [toDate], [img], [price], [quota], [dateImport], [destination], [statusID]) VALUES (N'T1', N'Travel To Greece', CAST(0x95400B00 AS Date), CAST(0xA7410B00 AS Date), N'img/greece.png', 1000, 5, CAST(0x95400B00 AS Date), N'Greece', 1)
INSERT [dbo].[tblTours] ([tourID], [tourName], [fromDate], [toDate], [img], [price], [quota], [dateImport], [destination], [statusID]) VALUES (N'T2', N'Travel to Thai', CAST(0x40410B00 AS Date), CAST(0x45410B00 AS Date), N'img/thai.png', 3000, 10, CAST(0x0E410B00 AS Date), N'Thai', 1)
INSERT [dbo].[tblTours] ([tourID], [tourName], [fromDate], [toDate], [img], [price], [quota], [dateImport], [destination], [statusID]) VALUES (N'T3', N'Travel to Australia', CAST(0x3E410B00 AS Date), CAST(0x47410B00 AS Date), N'img/australia.png', 300, 10, CAST(0x0E410B00 AS Date), N'Australia', 1)
INSERT [dbo].[tblTours] ([tourID], [tourName], [fromDate], [toDate], [img], [price], [quota], [dateImport], [destination], [statusID]) VALUES (N'T4', N'Travel to VietNam', CAST(0x47410B00 AS Date), CAST(0x54410B00 AS Date), N'img/vietnam.png', 500, 20, CAST(0x33410B00 AS Date), N'Viet Nam', 1)
INSERT [dbo].[tblTours] ([tourID], [tourName], [fromDate], [toDate], [img], [price], [quota], [dateImport], [destination], [statusID]) VALUES (N'T5', N'Travel to Sydney', CAST(0x43410B00 AS Date), CAST(0x45410B00 AS Date), N'img/australia.png', 1000, 10, CAST(0x3D410B00 AS Date), N'Sydney', 1)
INSERT [dbo].[tblTours] ([tourID], [tourName], [fromDate], [toDate], [img], [price], [quota], [dateImport], [destination], [statusID]) VALUES (N'T6', N'Travel to Ancient Greece', CAST(0x42410B00 AS Date), CAST(0x45410B00 AS Date), N'img/greece.png', 2000, 5, CAST(0x3D410B00 AS Date), N'Greece', 1)
/****** Object:  Table [dbo].[tblUsers]    Script Date: 06/22/2020 23:38:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblUsers](
	[username] [varchar](50) NOT NULL,
	[password] [varchar](50) NULL,
	[roleID] [int] NULL,
	[statusID] [int] NULL,
	[fullname] [varchar](50) NULL,
 CONSTRAINT [PK_tblUsers] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblUsers] ([username], [password], [roleID], [statusID], [fullname]) VALUES (N'admin', N'1', 1, 1, N'Admin')
INSERT [dbo].[tblUsers] ([username], [password], [roleID], [statusID], [fullname]) VALUES (N'user', N'1', 2, 1, N'Phuong')
/****** Object:  Table [dbo].[tblUsedDiscount]    Script Date: 06/22/2020 23:38:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblUsedDiscount](
	[id] [varchar](50) NOT NULL,
	[username] [varchar](50) NULL,
	[discountID] [varchar](50) NULL,
 CONSTRAINT [PK_tblUsedDiscount] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblUsedDiscount] ([id], [username], [discountID]) VALUES (N'U1', N'user', N'D1')
/****** Object:  Table [dbo].[tblBooking]    Script Date: 06/22/2020 23:38:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblBooking](
	[bookingID] [varchar](50) NOT NULL,
	[amount] [int] NULL,
	[total] [int] NULL,
	[date] [date] NULL,
	[username] [varchar](50) NULL,
	[tourID] [varchar](50) NULL,
	[statusID] [int] NULL,
	[discountID] [varchar](50) NULL,
 CONSTRAINT [PK_tblBooking] PRIMARY KEY CLUSTERED 
(
	[bookingID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblBooking] ([bookingID], [amount], [total], [date], [username], [tourID], [statusID], [discountID]) VALUES (N'B1', 2, 2000, CAST(0x09410B00 AS Date), N'user', N'T1', 1, NULL)
INSERT [dbo].[tblBooking] ([bookingID], [amount], [total], [date], [username], [tourID], [statusID], [discountID]) VALUES (N'B2', 1, 1000, CAST(0x09410B00 AS Date), N'user', N'T1', 1, NULL)
INSERT [dbo].[tblBooking] ([bookingID], [amount], [total], [date], [username], [tourID], [statusID], [discountID]) VALUES (N'B3', 1, 300, CAST(0x3D410B00 AS Date), N'user', N'T3', 1, NULL)
INSERT [dbo].[tblBooking] ([bookingID], [amount], [total], [date], [username], [tourID], [statusID], [discountID]) VALUES (N'B4', 1, 2400, CAST(0x3D410B00 AS Date), N'user', N'T2', 1, N'D1')
INSERT [dbo].[tblBooking] ([bookingID], [amount], [total], [date], [username], [tourID], [statusID], [discountID]) VALUES (N'B5', 1, 240, CAST(0x3D410B00 AS Date), N'user', N'T3', 1, N'D1')
INSERT [dbo].[tblBooking] ([bookingID], [amount], [total], [date], [username], [tourID], [statusID], [discountID]) VALUES (N'B6', 1, 800, CAST(0x3D410B00 AS Date), N'user', N'T5', 1, N'D1')
/****** Object:  ForeignKey [FK_tblTours_tblStatus]    Script Date: 06/22/2020 23:38:19 ******/
ALTER TABLE [dbo].[tblTours]  WITH CHECK ADD  CONSTRAINT [FK_tblTours_tblStatus] FOREIGN KEY([statusID])
REFERENCES [dbo].[tblStatus] ([statusID])
GO
ALTER TABLE [dbo].[tblTours] CHECK CONSTRAINT [FK_tblTours_tblStatus]
GO
/****** Object:  ForeignKey [FK_tblUsers_tblRoles]    Script Date: 06/22/2020 23:38:19 ******/
ALTER TABLE [dbo].[tblUsers]  WITH CHECK ADD  CONSTRAINT [FK_tblUsers_tblRoles] FOREIGN KEY([roleID])
REFERENCES [dbo].[tblRoles] ([roleID])
GO
ALTER TABLE [dbo].[tblUsers] CHECK CONSTRAINT [FK_tblUsers_tblRoles]
GO
/****** Object:  ForeignKey [FK_tblUsers_tblStatus]    Script Date: 06/22/2020 23:38:19 ******/
ALTER TABLE [dbo].[tblUsers]  WITH CHECK ADD  CONSTRAINT [FK_tblUsers_tblStatus] FOREIGN KEY([statusID])
REFERENCES [dbo].[tblStatus] ([statusID])
GO
ALTER TABLE [dbo].[tblUsers] CHECK CONSTRAINT [FK_tblUsers_tblStatus]
GO
/****** Object:  ForeignKey [FK_tblUsedDiscount_tblDiscount]    Script Date: 06/22/2020 23:38:19 ******/
ALTER TABLE [dbo].[tblUsedDiscount]  WITH CHECK ADD  CONSTRAINT [FK_tblUsedDiscount_tblDiscount] FOREIGN KEY([discountID])
REFERENCES [dbo].[tblDiscount] ([discountID])
GO
ALTER TABLE [dbo].[tblUsedDiscount] CHECK CONSTRAINT [FK_tblUsedDiscount_tblDiscount]
GO
/****** Object:  ForeignKey [FK_tblUsedDiscount_tblUsers]    Script Date: 06/22/2020 23:38:19 ******/
ALTER TABLE [dbo].[tblUsedDiscount]  WITH CHECK ADD  CONSTRAINT [FK_tblUsedDiscount_tblUsers] FOREIGN KEY([username])
REFERENCES [dbo].[tblUsers] ([username])
GO
ALTER TABLE [dbo].[tblUsedDiscount] CHECK CONSTRAINT [FK_tblUsedDiscount_tblUsers]
GO
/****** Object:  ForeignKey [FK_tblBooking_tblTours]    Script Date: 06/22/2020 23:38:19 ******/
ALTER TABLE [dbo].[tblBooking]  WITH CHECK ADD  CONSTRAINT [FK_tblBooking_tblTours] FOREIGN KEY([tourID])
REFERENCES [dbo].[tblTours] ([tourID])
GO
ALTER TABLE [dbo].[tblBooking] CHECK CONSTRAINT [FK_tblBooking_tblTours]
GO
/****** Object:  ForeignKey [FK_tblBooking_tblUsers]    Script Date: 06/22/2020 23:38:19 ******/
ALTER TABLE [dbo].[tblBooking]  WITH CHECK ADD  CONSTRAINT [FK_tblBooking_tblUsers] FOREIGN KEY([username])
REFERENCES [dbo].[tblUsers] ([username])
GO
ALTER TABLE [dbo].[tblBooking] CHECK CONSTRAINT [FK_tblBooking_tblUsers]
GO
