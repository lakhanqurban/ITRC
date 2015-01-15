
#install devtools

# install.packages("devtools")
# 
# #install shiny apps
# devtools::install_github('rstudio/shinyapps')
# 
# #load shiny library
# 
library(shinyapps)
library(shiny)
# 
# shinyapps::setAccountInfo(name='rischan', 
#                           token='130DB1BB36D38339FCCEC48C016D5D97',
#                           secret='863pLqbBofiKf67TBmmwqWIkCKzyJU3gXgi2NfXh')
# 
# # #install ggplot2 and shiny
# # install.packages(c('ggplot2','shiny'))
# 
# # list.files()
# # getwd()
# # setwd('C:/Users/rischan/Dropbox/shiny/archive/code/')
# # library(shiny)
setwd('D:/DATA/code')
runApp("datalog",display.mode = "showcase")

setwd('D:/ITRC/')
runApp("stockVis",display.mode = "showcase")

setwd('D:/ITRC/')
runApp("pie",display.mode = "showcase")
# 
# setwd('D:/ITRC/datalog')
# library(shinyapps)
# deployApp()
# 
# 
# # library(shiny)
# # runExample("02_text", display.mode = "showcase")
# 
# 
shinyapps::setAccountInfo(
  name="netsys", 
  token="353A4F40A88D2A3E7AD9E28CF5843C13", 
  secret="JUs08Ds0fqBnC94DTgm2rVtNoPNwvQvDTVTBEjpP")



setwd('D:/ITRC/datalog')
library(shinyapps)
deployApp()

