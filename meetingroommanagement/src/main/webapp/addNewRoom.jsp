<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="e"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>
<h3>Fill in the details to add new meeting room !</h3>
<form action="http://localhost:8080/meetingroommanagement/meetingroom" method=POST>
   Meeting Room Id : 
   <input type="text" name="mid" ><p>
   Meeting Room Name : 
   <input type="text" name="mname"><p>
   Capacity of the Room : 
   <input type="text" name="mcapacity"><p>
   
   Amenities : <p>
   <input type="checkbox"  name="amenities" value="Projector"> Projector  
   <input type="checkbox"  name="amenities" value="Wifi-Connection"> Wifi-Connection 
   <input type="checkbox"  name="amenities" value="Conference-Call-Facility"> Conference Call Facility
   <input type="checkbox"  name="amenities" value="White-Board"> White-Board
   <input type="checkbox"  name="amenities" value="Water-Dispenser"> Water Dispenser
   <input type="checkbox"  name="amenities" value="TV"> TV
   <input type="checkbox"  name="amenities" value="Coffee-Machine"> Coffee Machine 
   <input type ="submit" value ="Add New Employee">  
  
</form>
<p>
</body>
</html>