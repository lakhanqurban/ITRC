library(shiny)

shinyUI(
     navbarPage("Data Log Report",
                tabPanel("Raw Data",
                         titlePanel("List of Probes"),
                         
                         # Sidebar with controls to provide a caption, select a dataset,
                         # and specify the number of observations to view. Note that
                         # changes made to the caption in the textInput control are
                         # updated in the output area immediately as you type
                         sidebarLayout(
                              sidebarPanel(
                                   
                                   selectInput("dataset", "Choose a probe:", 
                                               choices = c("dActivityProbe", "dApplicationsProbe",
                                                           "dBluetoothProbe","dBatteryProbe",
                                                           "dCallLogProbe","dSimpleLocationProbe","dSmsProbe",
                                                           "dHardwareInfoProbe","dBrowserSearchesProbe",
                                                           "dBrowserBookmarksProbe","dLightSensorProbe",
                                                           "dMagneticFieldSensorProbe","dPressureSensorProbe",
                                                           "dProximitySensorProbe","dScreenProbe",
                                                           "dWifiProbe","dRunningApplicationsProbe")),
                                   downloadButton('downloadData', 'Download Data'),
                                   numericInput("obs", "Number of observations to view:", 10)
                                   
                              ),
                              
                              
                              # Show the caption, a summary of the dataset and an HTML 
                              # table with the requested number of observations
                              mainPanel(
                                   h3(textOutput("caption", container = span)),
                                   verbatimTextOutput("summary"), 
                                   tableOutput("view")
                              )
                         )
                         
                         
                         ),
                tabPanel("Basic Table",
                         titlePanel("Data Tables Format"),
                         
                         sidebarLayout(
                           sidebarPanel(
                             conditionalPanel(
                               'input.dataset2 === "dActivityProbe"',
                               checkboxGroupInput('show_vars', 'Columns in dActivityProbe to show:',
                                                  names(dActivityProbe), selected = names(dActivityProbe))
                             ),
                             conditionalPanel(
                               'input.dataset2 === "dApplicationsProbe"',
                               helpText('Click the column header to sort a column.')
                             ),
                             conditionalPanel(
                               'input.dataset2 === "dBluetoothProbe"',
                               helpText('Display 5 records by default.')
                             ),
                             conditionalPanel(
                               'input.dataset2 === "dBatteryProbe"',
                               helpText('Display 5 records by default.')
                             ),
                             conditionalPanel(
                               'input.dataset2 === "dCallLogProbe"',
                               helpText('Click the column header to sort a column.')
                             ),
                             conditionalPanel(
                               'input.dataset2 === "dSimpleLocationProbe"',
                               helpText('Display 5 records by default.')
                             ),
                             conditionalPanel(
                               'input.dataset2 === "dSmsProbe"',
                               helpText('Display 5 records by default.')
                             ),
                             conditionalPanel(
                               'input.dataset2 === "dHardwareInfoProbe"',
                               helpText('Click the column header to sort a column.')
                             ),
                             conditionalPanel(
                               'input.dataset2 === "dBrowserSearchesProbe"',
                               helpText('Display 5 records by default.')
                             ),
                             conditionalPanel(
                               'input.dataset2 === "dBrowserBookmarksProbe"',
                               helpText('Display 5 records by default.')
                             )
                           ),
                           mainPanel(
                             tabsetPanel(
                               id = 'dataset2',
                               tabPanel('dActivityProbe', dataTableOutput('mytable1')),
                               tabPanel('dApplicationsProbe', dataTableOutput('mytable2')),
                               tabPanel('dBluetoothProbe', dataTableOutput('mytable3')),
                               tabPanel('dBatteryProbe', dataTableOutput('mytable4')),
                               tabPanel('dCallLogProbe', dataTableOutput('mytable5')),
                               tabPanel('dSimpleLocationProbe', dataTableOutput('mytable6')),
                               tabPanel('dSmsProbe', dataTableOutput('mytable7')),
                               tabPanel('dHardwareInfoProbe', dataTableOutput('mytable8')),
                               tabPanel('dBrowserSearchesProbe', dataTableOutput('mytable9')),
                               tabPanel('dBrowserBookmarksProbe', dataTableOutput('mytable10'))
                             )
                           )
                         )
                         ),
                
                
                tabPanel("Activity Probe",
                         tabsetPanel("Panel 1.x",
                                     tabPanel("Summary", 
                                              
                                              titlePanel("Activity Summary"),
                                              
                                              # Sidebar with a slider input for the number of bins
                                              sidebarLayout(
                                                sidebarPanel(
                                                  sliderInput("bins",
                                                              "Number of bins:",
                                                              min = 1,
                                                              max = 50,
                                                              value = 30)
                                                ),
                                                
                                                # Show a plot of the generated distribution
                                                mainPanel(
                                                  plotOutput("distPlot")
                                                )
                                              )
                                              
                                              
                                              
                                              
                                              
                                              
                                     ),
                                     tabPanel("Time Series",
                                              titlePanel("Time Series Activity Probe Plot"),
                                              mainPanel(
                                                plotOutput("Activity_time_series")
                                              )
                                              
                                              
                                     )
                         )),
                
                
                tabPanel("Example Plot",
                         
                         tabsetPanel("Panel 1.x",
                           
                                     tabPanel("User Acticity ",
                                              titlePanel("Pie Chart"),
                                              mainPanel(
                                                plotOutput("pie_chart_activity")
                                              )
                                              
                                              
                                     ),
                                     tabPanel("Nearby Bluetooth ",
                                              titlePanel("Pie Chart"),
                                              mainPanel(
                                                plotOutput("pie_chart_bluetooth")
                                              )
                                              
                                              
                                     ),
                                     tabPanel("Nearby Access Point ",
                                              titlePanel("Histogram"),
                                              mainPanel(
                                                plotOutput("hist_wifi")
                                              )
                                              
                                              
                                     )
                       
                         )),
                tabPanel("Example Location",
                         tabPanel("Location Plot GGMAP ",
                                  titlePanel("GPS"),
                                  mainPanel(
                                    plotOutput("ggmap_gps")
                                  )
                                  
                                  
                         )
                         
                         ),
                tabPanel("Regression Location ",
                         sidebarPanel(
                           helpText(),
                           selectInput('x', 'Build a regression model of latitude against:',
                                       choices = names(location)[3]),
                           radioButtons('format', 'Document format', c('PDF', 'HTML', 'Word'),
                                        inline = TRUE),
                           downloadButton('downloadReport')
                         ),
                         mainPanel(
                           plotOutput('regPlot')
                         )
                         
                )
     )
)