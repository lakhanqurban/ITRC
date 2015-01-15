library(shiny)
library("RSQLite")
library("rjson")
library("ggplot2")
library("scales")
library("ggmap")
library("rmarkdown")
library("rmarkdown")

driver <- dbDriver("SQLite")
#setwd("./db")
setwd("D:/DATA/code/datalog/db")
#list.files()


file_list <- list.files()

for (file in file_list){
     
     # if the merged dataset doesn't exist, create it
     if (!exists("dataset")){
          con <- dbConnect(driver,file)
          dataset <- dbGetQuery(con,"Select * from data")
     }
     
     # if the merged dataset does exist, append to it
     if (exists("dataset")){
          con <- dbConnect(driver,file)
          temp_dataset <-dbGetQuery(con,"Select * from data")
          dataset<-rbind(dataset, temp_dataset)
          rm(temp_dataset)
     }
     
}

#List of Dataset
dActivityProbe  <- subset(dataset, dataset$name=="ActivityProbe")
dApplicationsProbe <- subset(dataset, dataset$name=="ApplicationsProbe")
dBluetoothProbe <- subset(dataset, dataset$name=="BluetoothProbe")
dBatteryProbe <- subset(dataset, dataset$name=="BatteryProbe")
dCallLogProbe <- subset(dataset, dataset$name=="CallLogProbe")
dContactProbe <- subset(dataset, dataset$name=="ContactProbe")
dHardwareInfoProbe <- subset(dataset, dataset$name=="HardwareInfoProbe")
dBrowserSearchesProbe <- subset(dataset, dataset$name=="BrowserSearchesProbe")
dBrowserBookmarksProbe <- subset(dataset, dataset$name=="BrowserBookmarksProbe")
dLightSensorProbe <- subset(dataset, dataset$name=="LightSensorProbe")
dMagneticFieldSensorProbe <- subset(dataset, dataset$name=="MagneticFieldSensorProbe")
dPressureSensorProbe <- subset(dataset, dataset$name=="PressureSensorProbe")
dProximitySensorProbe <- subset(dataset, dataset$name=="ProximitySensorProbe")
dRunningApplicationsProbe <- subset(dataset, dataset$name=="RunningApplicationsProbe")
dScreenProbe <- subset(dataset, dataset$name=="ScreenProbe")
dSimpleLocationProbe <- subset(dataset, dataset$name=="SimpleLocationProbe")
dSmsProbe <- subset(dataset, dataset$name=="SmsProbe")
dWifiProbe <- subset(dataset, dataset$name=="WifiProbe")

#dataset <- NULL



#RJSON PRocessing

#dActivityProbe
head(dActivityProbe)
myData <- lapply(dActivityProbe$value, fromJSON)
dActivityProbe$value <- as.character(do.call(rbind,lapply(myData, `[` ,'activityLevel')))   
dActivityProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp')) 
dActivityProbe$time <- as.character(as.POSIXlt(as.numeric(dActivityProbe$time), origin="1970-01-01", tz = "GMT"))
dA <- cbind(dActivityProbe$value,dActivityProbe$time)
dActivityProbe <- as.data.frame(dA)
names(dActivityProbe) <- c("Activity","Time")
# row.names(dActivityProbe)<-NULL
# activity <- unlist(dActivityProbe$value)
# date_time <- as.POSIXlt(as.numeric(dActivityProbe$time), origin="1970-01-01", tz = "GMT")


#dApplicationsProbe

head(dApplicationsProbe)
myData <- lapply(dApplicationsProbe$value, fromJSON)
dApplicationsProbe$value <- as.character(do.call(rbind,lapply(myData, `[` ,'publicSourceDir')))   
dApplicationsProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))   
dApplicationsProbe$time <- as.character(as.POSIXlt(as.numeric(dApplicationsProbe$time), origin="1970-01-01", tz = "GMT"))
dA <- cbind(dApplicationsProbe$value,dApplicationsProbe$time)
dApplicationsProbe <- as.data.frame(dA)
names(dApplicationsProbe) <- c("Application","Time")

#dBluetoothProbe

head(dBluetoothProbe)
myData <- lapply(dBluetoothProbe$value, fromJSON)  
dBluetoothProbe$device <- as.character(do.call(rbind,lapply(myData, `[` ,'android.bluetooth.device.extra.NAME')))  
dBluetoothProbe$RSSI <- as.character(do.call(rbind,lapply(myData, `[` ,'android.bluetooth.device.extra.RSSI')))  
dBluetoothProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))   
dBluetoothProbe$time <- as.character(as.POSIXlt(as.numeric(dBluetoothProbe$time), origin="1970-01-01", tz = "GMT"))
dA <- cbind(dBluetoothProbe$device,dBluetoothProbe$RSSI, dBluetoothProbe$time)
dBluetoothProbe <- as.data.frame(dA)
names(dBluetoothProbe) <- c("BluetoothName","RSSI","Time")
#dBatteryProbe

#dBatteryProbe

head(dBatteryProbe)
myData <- lapply(dBatteryProbe$value, fromJSON)  
dBatteryProbe$charge_type <- as.character(do.call(rbind,lapply(myData, `[` ,'charge_type')))
dBatteryProbe$charge_type[dBatteryProbe$charge_type=="0"] <- "AC"
dBatteryProbe$charge_type[dBatteryProbe$charge_type=="1"] <- "USB"
dBatteryProbe$charge_type[dBatteryProbe$charge_type=="4"] <- "Wireless"
dBatteryProbe$health <- as.character(do.call(rbind,lapply(myData, `[` ,'health'))) 
dBatteryProbe$health[dBatteryProbe$health=="1"] <- "unknown"
dBatteryProbe$health[dBatteryProbe$health=="2"] <- "good"
dBatteryProbe$health[dBatteryProbe$health=="3"] <- "overheat"
dBatteryProbe$health[dBatteryProbe$health=="4"] <- "dead"
dBatteryProbe$health[dBatteryProbe$health=="5"] <- "over_voltage"
dBatteryProbe$health[dBatteryProbe$health=="6"] <- "unspecified_failure"
dBatteryProbe$health[dBatteryProbe$health=="7"] <- "cold"
dBatteryProbe$plugged <- as.character(do.call(rbind,lapply(myData, `[` ,'plugged'))) 
dBatteryProbe$plugged[dBatteryProbe$plugged=="0"] <- "on_battery"
dBatteryProbe$plugged[dBatteryProbe$plugged=="2"] <- "power_source"
dBatteryProbe$status <- as.character(do.call(rbind,lapply(myData, `[` ,'status')))  
dBatteryProbe$status[dBatteryProbe$status=="1"] <- "unknown"
dBatteryProbe$status[dBatteryProbe$status=="2"] <- "charging"
dBatteryProbe$status[dBatteryProbe$status=="3"] <- "discharging"
dBatteryProbe$status[dBatteryProbe$status=="4"] <- "not_charging"
dBatteryProbe$status[dBatteryProbe$status=="5"] <- "full"
dBatteryProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))
dBatteryProbe$time <- as.character(as.POSIXlt(as.numeric(dBatteryProbe$time), origin="1970-01-01", tz = "GMT"))
dA <- cbind(dBatteryProbe$charge_type,dBatteryProbe$health,dBatteryProbe$plugged,dBatteryProbe$status,dBatteryProbe$time)
dBatteryProbe <- as.data.frame(dA)
names(dBatteryProbe) <- c("charge_type","health","plugged","status","time")


#dCallLogProbe
head(dCallLogProbe)
myData <- lapply(dCallLogProbe$value, fromJSON)  
dCallLogProbe$date <- do.call(rbind,lapply(myData, `[` ,'date')) 
dCallLogProbe$time_calling <- as.character(as.POSIXlt(as.numeric(substr(dCallLogProbe$date, 1, 10)), origin="1970-01-01", tz = "GMT"))
dCallLogProbe$duration <- as.character(do.call(rbind,lapply(myData, `[` ,'duration'))) 
#dCallLogProbe$number <- as.character(do.call(rbind,lapply(myData, `[` ,'number')))  
dCallLogProbe$type <- as.character(do.call(rbind,lapply(myData, `[` ,'type')))  
dCallLogProbe$type[dCallLogProbe$type=="1"] <- "incoming"
dCallLogProbe$type[dCallLogProbe$type=="2"] <- "outgoing"
dCallLogProbe$type[dCallLogProbe$type=="3"] <- "missed"
dA <- cbind(dCallLogProbe$type,dCallLogProbe$time_calling,dCallLogProbe$duration)
dCallLogProbe <- as.data.frame(dA)
names(dCallLogProbe) <- c("type","time","duration")



#dSmsProbe
head(dSmsProbe)
myData <- lapply(dSmsProbe$value, fromJSON)  
dSmsProbe$date <- do.call(rbind,lapply(myData, `[` ,'date')) 
dSmsProbe$time <- as.character(as.POSIXlt(as.numeric(substr(dSmsProbe$date, 1, 10)), origin="1970-01-01", tz = "GMT"))
dSmsProbe$address <- as.character(do.call(rbind,lapply(myData, `[` ,'address')))  
dSmsProbe$type <- as.character(do.call(rbind,lapply(myData, `[` ,'type')))  
dSmsProbe$type[dSmsProbe$type=="0"] <- "all"
dSmsProbe$type[dSmsProbe$type=="1"] <- "inbox"
dSmsProbe$type[dSmsProbe$type=="2"] <- "sent"
dSmsProbe$type[dSmsProbe$type=="3"] <- "draft"
dSmsProbe$type[dSmsProbe$type=="4"] <- "outbox"
dSmsProbe$type[dSmsProbe$type=="5"] <- "failed"
dSmsProbe$type[dSmsProbe$type=="6"] <- "queued"
dSmsProbe$status <- as.character(do.call(rbind,lapply(myData, `[` ,'status'))) 
dSmsProbe$status[dSmsProbe$status=="-1"] <- "none"
dSmsProbe$status[dSmsProbe$status=="0"] <- "complete"
dSmsProbe$status[dSmsProbe$status=="32"] <- "pending"
dSmsProbe$status[dSmsProbe$status=="64"] <- "failed"
# dSmsProbe$body-byte-len <- as.character(do.call(rbind,lapply(myData, `[` ,'body-byte-len')))  
# dSmsProbe$body-token-byte-len <- as.character(do.call(rbind,lapply(myData, `[` ,'body-token-byte-len')))  
# dSmsProbe$body-token-count <- as.character(do.call(rbind,lapply(myData, `[` ,'body-token-count')))  
dA <- cbind(dSmsProbe$time, dSmsProbe$type, dSmsProbe$address,dSmsProbe$status)
dSmsProbe <- as.data.frame(dA)
names(dSmsProbe) <- c("when", "type","address","status")


# 
# dContactProbe <- subset(dataset, dataset$name=="ContactProbe")
# myData <- lapply(dContactProbe$value, fromJSON)  
# dContactProbe$date <- do.call(rbind,lapply(myData, `[` ,'date')) 
# dContactProbe$time_calling <- as.character(as.POSIXlt(as.numeric(substr(dContactProbe$date, 1, 10)), origin="1970-01-01", tz = "GMT"))
# dContactProbe$duration <- as.character(do.call(rbind,lapply(myData, `[` ,'duration'))) 
# dContactProbe$number <- as.character(do.call(rbind,lapply(myData, `[` ,'number')))  
# dContactProbe$type <- as.character(do.call(rbind,lapply(myData, `[` ,'type')))  
# dContactProbe$type[dContactProbe$type=="1"] <- "incoming"
# dContactProbe$type[dContactProbe$type=="2"] <- "outgoing"
# dContactProbe$type[dContactProbe$type=="3"] <- "missed"
# dA <- cbind(dContactProbe$type, dContactProbe$number,dContactProbe$time_calling,dContactProbe$duration)
# dContactProbe <- as.data.frame(dA)
# names(dContactProbe) <- c("type","number","time","duration")
# summary(dContactProbe)


#dSimpleLocationProbe
head(dSimpleLocationProbe)
myData <- lapply(dSimpleLocationProbe$value, fromJSON)  
dSimpleLocationProbe$Latitude <- as.character(do.call(rbind,lapply(myData, `[` ,'mLatitude')))  
dSimpleLocationProbe$Longitude <- as.character(do.call(rbind,lapply(myData, `[` ,'mLongitude')))  
dSimpleLocationProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))  
dSimpleLocationProbe$when <- as.character(as.POSIXlt(as.numeric(dSimpleLocationProbe$time), origin="1970-01-01", tz = "GMT"))
dA <- cbind(dSimpleLocationProbe$when, dSimpleLocationProbe$Latitude , dSimpleLocationProbe$Longitude)
dSimpleLocationProbe <- as.data.frame(dA)
names(dSimpleLocationProbe) <- c("when","latitude","longitude")


location <-dSimpleLocationProbe
location$latitude <- as.numeric(as.character(dSimpleLocationProbe$latitude))
location$longitude <- as.numeric(as.character(dSimpleLocationProbe$longitude))

#dHardwareInfoProbe

head(dHardwareInfoProbe)
myData <- lapply(dHardwareInfoProbe$value, fromJSON) 
dHardwareInfoProbe$brand <- as.character(do.call(rbind,lapply(myData, `[` ,'brand')))
dHardwareInfoProbe$model <- as.character(do.call(rbind,lapply(myData, `[` ,'model')))
dHardwareInfoProbe$bluetoothMac <- as.character(do.call(rbind,lapply(myData, `[` ,'bluetoothMac')))
dHardwareInfoProbe$wifiMac <- as.character(do.call(rbind,lapply(myData, `[` ,'wifiMac')))
dA <- cbind(dHardwareInfoProbe$brand,dHardwareInfoProbe$model,dHardwareInfoProbe$bluetoothMac,dHardwareInfoProbe$wifiMac)
dHardwareInfoProbe <- as.data.frame(dA)
names(dHardwareInfoProbe) <- c("brand","model","bluetoothMac", "wifiMac")




#dBrowserSearchesProbe
head(dBrowserSearchesProbe)
myData <- lapply(dBrowserSearchesProbe$value, fromJSON) 
dBrowserSearchesProbe$time <- do.call(rbind,lapply(myData, `[` ,'date'))  
dBrowserSearchesProbe$when <- as.character(as.POSIXlt(as.numeric(dBrowserSearchesProbe$time), origin="1970-01-01", tz = "GMT"))
dBrowserSearchesProbe$query <- as.character(do.call(rbind,lapply(myData, `[` ,'search')))
dA <- cbind(dBrowserSearchesProbe$when,dBrowserSearchesProbe$query)
dBrowserSearchesProbe <- as.data.frame(dA)
names(dBrowserSearchesProbe) <- c("when","query")



#dBrowserBookmarksProbe
head(dBrowserBookmarksProbe)
myData <- lapply(dBrowserBookmarksProbe$value, fromJSON) 
dBrowserBookmarksProbe$time <- do.call(rbind,lapply(myData, `[` ,'created'))  
dBrowserBookmarksProbe$when <- as.character(as.POSIXlt(as.numeric(dBrowserBookmarksProbe$time), origin="1970-01-01", tz = "GMT"))
dBrowserBookmarksProbe$title <- as.character(do.call(rbind,lapply(myData, `[` ,'title')))
dBrowserBookmarksProbe$url <- as.character(do.call(rbind,lapply(myData, `[` ,'url')))
dA <- cbind(dBrowserBookmarksProbe$when,dBrowserBookmarksProbe$title, dBrowserBookmarksProbe$url)
dBrowserBookmarksProbe <- as.data.frame(dA)
names(dBrowserBookmarksProbe) <- c("created","title","url")




#dLightSensorProbe
head(dLightSensorProbe)
myData <- lapply(dLightSensorProbe$value, fromJSON) 
dLightSensorProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))  
dLightSensorProbe$timestamp <- as.character(as.POSIXlt(as.numeric(dLightSensorProbe$time), origin="1970-01-01", tz = "GMT"))
dLightSensorProbe$accuracy <- as.character(do.call(rbind,lapply(myData, `[` ,'accuracy')))
dLightSensorProbe$lux <- as.character(do.call(rbind,lapply(myData, `[` ,'lux')))
dA <- cbind(dLightSensorProbe$timestamp,dLightSensorProbe$accuracy, dLightSensorProbe$lux)
dLightSensorProbe <- as.data.frame(dA)
names(dLightSensorProbe) <- c("timestamp","accuracy","lux")




#dMagneticFieldSensorProbe
head(dMagneticFieldSensorProbe)
myData <- lapply(dMagneticFieldSensorProbe$value, fromJSON) 
dMagneticFieldSensorProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))  
dMagneticFieldSensorProbe$timestamp <- as.character(as.POSIXlt(as.numeric(dMagneticFieldSensorProbe$time), origin="1970-01-01", tz = "GMT"))
dMagneticFieldSensorProbe$accuracy <- as.character(do.call(rbind,lapply(myData, `[` ,'accuracy')))
dMagneticFieldSensorProbe$accuracy[dMagneticFieldSensorProbe$accuracy=="1"] <- "low"
dMagneticFieldSensorProbe$accuracy[dMagneticFieldSensorProbe$accuracy=="2"] <- "medium"
dMagneticFieldSensorProbe$accuracy[dMagneticFieldSensorProbe$accuracy=="3"] <- "high"
dMagneticFieldSensorProbe$x <- as.character(do.call(rbind,lapply(myData, `[` ,'x')))
dMagneticFieldSensorProbe$y <- as.character(do.call(rbind,lapply(myData, `[` ,'y')))
dMagneticFieldSensorProbe$z <- as.character(do.call(rbind,lapply(myData, `[` ,'z')))
dA <- cbind(dMagneticFieldSensorProbe$timestamp,dMagneticFieldSensorProbe$accuracy, dMagneticFieldSensorProbe$x,dMagneticFieldSensorProbe$y, dMagneticFieldSensorProbe$z)
dMagneticFieldSensorProbe <- as.data.frame(dA)
names(dMagneticFieldSensorProbe) <- c("timestamp","accuracy","x","y","z")





#dPressureSensorProbe 
head(dPressureSensorProbe)
myData <- lapply(dPressureSensorProbe$value, fromJSON)
dPressureSensorProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))  
dPressureSensorProbe$timestamp <- as.character(as.POSIXlt(as.numeric(dPressureSensorProbe$time), origin="1970-01-01", tz = "GMT"))
dPressureSensorProbe$accuracy <- as.character(do.call(rbind,lapply(myData, `[` ,'accuracy')))
dPressureSensorProbe$pressure <- as.character(do.call(rbind,lapply(myData, `[` ,'pressure')))
dA <- cbind(dPressureSensorProbe$timestamp,dPressureSensorProbe$accuracy, dPressureSensorProbe$pressure)
dPressureSensorProbe <- as.data.frame(dA)
names(dPressureSensorProbe) <- c("timestamp","accuracy","pressure")




#dProximitySensorProbe 

head(dProximitySensorProbe)
myData <- lapply(dProximitySensorProbe$value, fromJSON)
dProximitySensorProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))  
dProximitySensorProbe$timestamp <- as.character(as.POSIXlt(as.numeric(dProximitySensorProbe$time), origin="1970-01-01", tz = "GMT"))
dProximitySensorProbe$accuracy <- as.character(do.call(rbind,lapply(myData, `[` ,'accuracy')))
dProximitySensorProbe$distance <- as.character(do.call(rbind,lapply(myData, `[` ,'distance')))
dA <- cbind(dProximitySensorProbe$timestamp,dProximitySensorProbe$accuracy, dProximitySensorProbe$distance)
dProximitySensorProbe <- as.data.frame(dA)
names(dProximitySensorProbe) <- c("timestamp","accuracy","distance")



#dScreenProbe 
head(dScreenProbe)
myData <- lapply(dScreenProbe$value, fromJSON)
dScreenProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))  
dScreenProbe$timestamp <- as.character(as.POSIXlt(as.numeric(dScreenProbe$time), origin="1970-01-01", tz = "GMT"))
dScreenProbe$screenOn <- as.character(do.call(rbind,lapply(myData, `[` ,'screenOn')))
dA <- cbind(dScreenProbe$timestamp, dScreenProbe$screenOn)
dScreenProbe <- as.data.frame(dA)
names(dScreenProbe) <- c("timestamp","screenON")



#dWifiProbe 
head(dWifiProbe)
myData <- lapply(dWifiProbe$value, fromJSON)
dWifiProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))  
dWifiProbe$timestamp <- as.character(as.POSIXlt(as.numeric(dWifiProbe$time), origin="1970-01-01", tz = "GMT"))
dWifiProbe$BSSID <- as.character(do.call(rbind,lapply(myData, `[` ,'BSSID')))
dWifiProbe$SSID <- as.character(do.call(rbind,lapply(myData, `[` ,'SSID')))
dWifiProbe$capabilities <- as.character(do.call(rbind,lapply(myData, `[` ,'capabilities')))
dWifiProbe$frequency <- as.character(do.call(rbind,lapply(myData, `[` ,'frequency')))
dWifiProbe$level <- as.character(do.call(rbind,lapply(myData, `[` ,'level')))
dA <- cbind(dWifiProbe$timestamp, dWifiProbe$BSSID,dWifiProbe$SSID,dWifiProbe$capabilities,dWifiProbe$frequency,dWifiProbe$level)
dWifiProbe <- as.data.frame(dA)
names(dWifiProbe) <- c("timestamp","BSSID","SSID","capabilities","frequency","level")



#dRunningApplicationsProbe 
head(dRunningApplicationsProbe)
myData <- lapply(dRunningApplicationsProbe$value, fromJSON)

dRunningApplicationsProbe$time <- do.call(rbind,lapply(myData, `[` ,'timestamp'))  
dRunningApplicationsProbe$timestamp <- as.character(as.POSIXlt(as.numeric(dRunningApplicationsProbe$time), origin="1970-01-01", tz = "GMT"))
dRunningApplicationsProbe$duration <- as.character(do.call(rbind,lapply(myData, `[` ,'duration')))
dRunningApplicationsProbe$package <- as.character(do.call(rbind,lapply(myData, `[` ,'taskInfo$baseIntent$mComponent$mPackage')))
dA <- cbind(dRunningApplicationsProbe$timestamp, dRunningApplicationsProbe$duration,dRunningApplicationsProbe$package)
dRunningApplicationsProbe <- as.data.frame(dA)
names(dRunningApplicationsProbe) <- c("timestamp","duration","package")






# Define server logic required to draw a histogram
shinyServer(function(input, output) {
     
     # Expression that generates a histogram. The expression is
     # wrapped in a call to renderPlot to indicate that:
     #
     #  1) It is "reactive" and therefore should re-execute automatically
     #     when inputs change
     #  2) Its output type is a plot
    
#   dActivityProbe <- "dActivityProbe"
#   dApplicationsProbe <- "dApplicationsProbe"
#   dBluetoothProbe <- "dBluetoothProbe"
  
     output$distPlot <- renderPlot({
          x    <- faithful[, 2]  # Old Faithful Geyser data
          bins <- seq(min(x), max(x), length.out = input$bins + 1)
          
          # draw the histogram with the specified number of bins
          hist(x, breaks = bins, col = 'darkgray', border = 'white')
     })
     
     # Return the requested dataset
     datasetInput <- reactive({
          switch(input$dataset,
                 "dActivityProbe" = dActivityProbe,
                 "dApplicationsProbe" = dApplicationsProbe,
                 "dBluetoothProbe" = dBluetoothProbe,
                 "dBatteryProbe" = dBatteryProbe,
                 "dCallLogProbe" = dCallLogProbe,
                 "dSimpleLocationProbe" = dSimpleLocationProbe,
                 "dSmsProbe" = dSmsProbe,
                 "dHardwareInfoProbe" = dHardwareInfoProbe,
                 "dBrowserSearchesProbe" = dBrowserSearchesProbe,
                 "dBrowserBookmarksProbe" = dBrowserBookmarksProbe,
                 "dLightSensorProbe"=dLightSensorProbe,
                 "dMagneticFieldSensorProbe" =dMagneticFieldSensorProbe,
                 "dPressureSensorProbe" =dPressureSensorProbe,
                 "dProximitySensorProbe" =dProximitySensorProbe,
                 "dScreenProbe"=dScreenProbe,
                 "dWifiProbe"=dWifiProbe,
                 "dRunningApplicationsProbe"=dRunningApplicationsProbe)
     })
     

     output$summary <- renderPrint({
       dataset <- datasetInput()
       summary(dataset)
     })
     # Show the first "n" observations
     output$view <- renderTable({
          head(datasetInput(), n = input$obs)
     })
     
     output$downloadData <- downloadHandler(
       filename = function() { 
         paste(input$dataset, '.csv', sep='') 
       },
       content = function(file) {
         write.csv(datasetInput(), file)
       }
     )
     
     output$Activity_time_series <- renderPlot({
          p <- qplot(data=dActivityProbe,,color=activity,x=date_time,y=activity)
          print(p)
          
     })
     
     output$pie_chart_activity <- renderPlot({
       pie <- ggplot(dActivityProbe, aes(x = "", fill = Activity)) +
         geom_bar(width = 1)
       pie + coord_polar(theta = "y")
       print(pie)
     }, bg="transparent")

     output$pie_chart_bluetooth <- renderPlot({
       pie <- ggplot(dBluetoothProbe, aes(x = "", fill = BluetoothName)) +
         geom_bar(width = 1)
       pie + coord_polar(theta = "y")
       print(pie)
     }, bg="transparent")
     
     output$hist_wifi <- renderPlot({
       dWifiProbe_small <- dWifiProbe[sample(nrow(dWifiProbe),60),]
       hist_cut <- ggplot(dWifiProbe_small, aes(x=SSID, fill=SSID)) +
         theme(text = element_text(size=10),axis.text.x = element_text(angle=30, vjust=1)) + geom_bar()
       
       print(hist_cut)
     }, bg="transparent")
     
     output$ggmap_gps <- renderPlot({
       setwd('D:/DATA/code/datalog/')
       pts <- dSimpleLocationProbe
       pts$longitude <- as.numeric(as.character(dSimpleLocationProbe$longitude))
       pts$latitude <- as.numeric(as.character(dSimpleLocationProbe$latitude))
       
       loc <- c(min(pts$longitude), min(pts$latitude), max(pts$longitude), max(pts$latitude))
       map <- get_map(location=loc, zoom=13, maptype="roadmap")
       p <- ggmap(map) + geom_path(aes(x=longitude, y=latitude), data=pts)
      
       print(p)
     }, bg="transparent")
     
     
     output$mytable1 <- renderDataTable({
       library(ggplot2)
       dActivityProbe[, input$show_vars, drop = FALSE]
     })
     
     # sorted columns are colored now because CSS are attached to them
     output$mytable2 <- renderDataTable({
       dApplicationsProbe
     }, options = list(orderClasses = TRUE))
     
     # customize the length drop-down menu; display 5 rows per page by default
     output$mytable3 <- renderDataTable({
       dBluetoothProbe
     }, options = list(lengthMenu = c(5, 30, 50), pageLength = 5))
     
     output$mytable4 <- renderDataTable({
       library(ggplot2)
       dBatteryProbe
     }, options = list(lengthMenu = c(5, 30, 50), pageLength = 5))  
     
     # sorted columns are colored now because CSS are attached to them
     output$mytable5 <- renderDataTable({
       dCallLogProbe
     }, options = list(orderClasses = TRUE))
     
     # customize the length drop-down menu; display 5 rows per page by default
     output$mytable6 <- renderDataTable({
       dSimpleLocationProbe
     }, options = list(lengthMenu = c(5, 30, 50), pageLength = 5))
     
     output$mytable7 <- renderDataTable({
       library(ggplot2)
       dSmsProbe
     }, options = list(lengthMenu = c(5, 30, 50), pageLength = 5))
     
     # sorted columns are colored now because CSS are attached to them
     output$mytable8 <- renderDataTable({
       dHardwareInfoProbe
     }, options = list(orderClasses = TRUE))
     
     # customize the length drop-down menu; display 5 rows per page by default
     output$mytable9 <- renderDataTable({
       dBrowserSearchesProbe
     }, options = list(lengthMenu = c(5, 30, 50), pageLength = 5))
     
     output$mytable10 <- renderDataTable({
       library(ggplot2)
       dBrowserBookmarksProbe
     }, options = list(lengthMenu = c(5, 30, 50), pageLength = 5))
     
     setwd('D:/DATA/code/datalog/')
     regFormula <- reactive({
       as.formula(paste('latitude ~', input$x))
     })
     
     output$regPlot <- renderPlot({
       fit <- lm(regFormula(), data = location)
       par(mar = c(4, 4, .1, .1))
       plot(regFormula(), data = location, pch = 19, col = 'green')
       abline(fit, col = 'red', lwd = 2)
     })
     
     output$downloadReport <- downloadHandler(
       filename = function() {
         paste('my-report', sep = '.', switch(
           input$format, PDF = 'pdf', HTML = 'html', Word = 'docx'
         ))
       },
       
       content = function(file) {
         src <- normalizePath('report.Rmd')
         
         # temporarily switch to the temp dir, in case you do not have write
         # permission to the current working directory
         owd <- setwd(tempdir())
         on.exit(setwd(owd))
         file.copy(src, 'report.Rmd')
         
         library(rmarkdown)
         out <- render('report.Rmd', switch(
           input$format,
           PDF = pdf_document(), HTML = html_document(), Word = word_document()
         ))
         file.rename(out, file)
       }
     )
})

