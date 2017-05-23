
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.FileReader;
import java.io.BufferedReader;

import java.lang.reflect.*;
import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;

/**
 * Todo computador tiene un disco duro (o varios)  que garantiza la persistencia de la información.
 * La clase DiscoDuro proporciona un conjunto de métodos que permiten
 * guardar objetos en un archivo de texto en el disco duro del computador y recuperarlo posteriormente.
 * Los formatos de almacenamiento son: 
 * --> binario como un flujo (stream), requiere implementar la interfaz Serializable 
 * --> CSV, que se puede abrir en una Hoja de Cálculo como Excel. Se usa separador de campos el punto y coma ";" y sin comillas para las cadenas.
 * En ésta versión inicial se soportan objetos sencillos.
 * @author (Milton Jesús Vera Contreras - miltonjesusvc@ufps.edu.co) 
 * @version 0.000000000000001 :) --> Math.sin(Math.PI-Double.MIN_VALUE)
 */
public class DiscoDuro
{
    /**Default Construtor*/
    public DiscoDuro(){}

    public void guardarBinario(Object objeto, String rutaArchivo) {
        try{
            FileOutputStream archivo = new FileOutputStream(rutaArchivo);
            ObjectOutputStream salida = new ObjectOutputStream(archivo);
            salida.writeObject(objeto);
            salida.flush();
            archivo.flush();
            archivo.close();
        }
        catch(Exception e){
            System.err.println("Algo salió mal " + e.getMessage());
            e.printStackTrace();
        }
    }//fin guardar

    public Object leerBinario(String rutaArchivo){
        try{
            FileInputStream archivo = new FileInputStream(rutaArchivo);
            ObjectInputStream entrada = new ObjectInputStream(archivo);
            Object objeto = entrada.readObject();
            return objeto;   
        }
        catch(Exception e){
            System.err.println("Algo salió mal " + e.getMessage());
            e.printStackTrace();
            return null;
        }   
    }//fin leer

    public boolean guardarCSV(Object objeto, String rutaArchivo) {
        try{
            FileOutputStream archivo = new FileOutputStream(rutaArchivo);
            PrintStream salida = new PrintStream(archivo);
            GetterSetterIntrospector gsi = new GetterSetterIntrospector(objeto);
            String [] properties = gsi.getPropertiesWithGetSet();
            String value=null;
            for(int i=0;i<properties.length;i++)
                salida.print(properties[i]+(i!=properties.length?";":""));
            salida.println();
            for(int i=0;i<properties.length;i++){
                value=gsi.invokeGetter(properties[i]);
                salida.print(value+(i!=properties.length?";":""));
            }
            salida.println();
            salida.flush();
            archivo.flush();
            return true;
        }
        catch(Exception e){
            System.err.println("Algo salió mal " + e.getMessage());
            e.printStackTrace();
            return false;
        }   
    }

    public boolean guardarCSV(Object [ ] objetos, String rutaArchivo) {
        try{
            FileOutputStream archivo = new FileOutputStream(rutaArchivo);
            PrintStream salida = new PrintStream(archivo);
            GetterSetterIntrospector gsi = new GetterSetterIntrospector(objetos[0]);
            String [] properties = gsi.getPropertiesWithGetSet();
            String value=null;
            for(int i=0;i<properties.length;i++)
                salida.print(properties[i]+(i!=properties.length?";":""));
            salida.println();
            for(int j=0; j<objetos.length;j++){
                gsi = new GetterSetterIntrospector(objetos[j]);
                for(int i=0;i<properties.length;i++){
                    value=gsi.invokeGetter(properties[i]);
                    salida.print(value+(i!=properties.length?";":""));
                }
                salida.println();
            }
            salida.flush();
            archivo.flush();
            return true;
        }
        catch(Exception e){
            System.err.println("Algo salió mal " + e.getMessage());
            e.printStackTrace();
            return false;
        }   
    }

    public boolean leerCSV(Object objeto, String rutaArchivo){
        try{
            FileReader archivo = new FileReader(rutaArchivo);
            BufferedReader entrada = new BufferedReader(archivo);
            GetterSetterIntrospector gsi = new GetterSetterIntrospector(objeto);
            String [] properties = entrada.readLine().split(";");
            String [] values = entrada.readLine().split(";");
            for(int i=0;i<properties.length;i++){
                String value = values[i];
                value = value.equals("null") ? null : value;
                if(! gsi.invokeSetter(properties[i], value)) System.err.println("Error al leer propiedad " + properties[i] + " valor " + values[i]);
            }
            archivo.close();
            return true;
        }
        catch(Exception e){
            System.err.println("Algo salió mal " + e.getMessage());
            e.printStackTrace();
            return false;
        }    
    }
    public String[] leerTamanio2CSV(String rutaArchivo){
        String []s;
        try{
            FileReader archivo = new FileReader(rutaArchivo);
            BufferedReader entrada = new BufferedReader(archivo);
            String linea = entrada.readLine();
            s=linea.split(",");
            return s;
        }
        catch(Exception e){
            System.err.println("Algo salió mal " + e.getMessage());
            e.printStackTrace();
            return null;
        }    
    }
    public int leerTamanioCSV(String rutaArchivo){
        try{
            FileReader archivo = new FileReader(rutaArchivo);
            BufferedReader entrada = new BufferedReader(archivo);
            String linea = entrada.readLine();
            int n = Integer.parseInt(linea);
            return n;
        }
        catch(Exception e){
            System.err.println("Algo salió mal " + e.getMessage());
            e.printStackTrace();
            return -1;
        }    
    }

    public void leerCSV(Object [ ] objetos, String rutaArchivo){
        try{
            FileReader archivo = new FileReader(rutaArchivo);
            BufferedReader entrada = new BufferedReader(archivo);
            GetterSetterIntrospector gsi = new GetterSetterIntrospector(objetos[0]);
            String ignorarLinea1 = entrada.readLine();
            String [] properties = entrada.readLine().split(";");
            for(int j=0;j<objetos.length;j++){
                gsi = new GetterSetterIntrospector(objetos[j]);
                String [] values = entrada.readLine().split(";");
                for(int i=0;i<properties.length;i++){
                    String value = values[i];
                    value = value.equals("null") ? null : value;                    
                    if(! gsi.invokeSetter(properties[i], value)) System.err.println("Error al leer propiedad " + properties[i] + " valor " + values[i]);
                }
            }
            archivo.close();
            return ;
        }
        catch(Exception e){
            System.err.println("Algo salió mal " + e.getMessage());
            e.printStackTrace();
            return ;
        }    
    }

    public String [] leerTextoPlano(String rutaArchivo){
        try{
            FileReader archivo = new FileReader(rutaArchivo);
            BufferedReader entrada = new BufferedReader(archivo);
            ArrayList arrayLineas = new ArrayList();
            String [] lineas = null;
            while(entrada.ready())
                arrayLineas.add(entrada.readLine());
            lineas = new String[arrayLineas.size()];
            for(int i=0;i<arrayLineas.size();i++) lineas[i]=arrayLineas.get(i).toString();
            archivo.close();
            return lineas;
        }
        catch(Exception e){
            System.err.println("Algo salió mal " + e.getMessage());
            e.printStackTrace();
            return null;
        }    
    }

    /**************************************************************************
     * Clase GetterSetterIntrospector.
     * Esta clase utiliza la API Java-reflect y mediante introspección permite obtener los metodos get y set y las propiedades que tienen tales metodos get y set
     * 
     * @author (Milton Jesús Vera Contreras) 
     * @version (Double.MIN_VALUE)
     */
    public class GetterSetterIntrospector
    {
        private Object theObject;
        private int theObjectPosition;
        private int totalObjects;
        private Class theClass;
        private Method [] theGetMethods;
        private Method [] theSetMethods;
        private String [] propertiesWithGetSet;

        private final String wrappers   = "Character,Byte     ,Short    ,Integer  ,Long     ,Float    ,Double   ,String   ,Boolean";
        private final String primitives = "char     ,byte     ,short    ,int      ,long     ,float    ,double   ,String   ,boolean";   

        public GetterSetterIntrospector(Class theClass) throws Exception
        {
            setTheClass(theClass);
        }//end constructor

        /***/
        public GetterSetterIntrospector(Object theObject) throws Exception
        {
            this.setTheObject(theObject);
        }//end constructor

        /**
         * Obtiene los metodos get y set del objeto.
         * Usa java.lang.Class en lugar de bluej.extensions.Class
         */
        private void introspectForGetterSetter()
        {
            Method [] methods = theClass.getMethods();
            ArrayList sets = new ArrayList();
            ArrayList gets = new ArrayList();

            for(int i=0; i<methods .length; i++){
                if(isGetMethod(methods[i]))
                    gets.add(methods[i]);
                else
                if(isSetMethod(methods[i]))
                    sets.add(methods[i]);
            }//end for

            this.theGetMethods = new Method[gets.size()];
            for(int i=0; i<gets.size();i++) this.theGetMethods[i] = (Method) gets.get(i);

            this.theSetMethods = new Method[sets.size()];
            for(int i=0; i<sets.size();i++) this.theSetMethods[i] = (Method) sets.get(i);     

            getPropertiesWithGetSet();

        }//end introspectForGetterSetter

        /**Determina si el metodo es get*/
        private boolean isGetMethod(Method theMethod)
        {
            boolean startsWithGet = theMethod.getName().startsWith("get") && theMethod.getName().length() > 3;
            boolean notHasParameters = theMethod.getParameterTypes().length==0;
            boolean hasReturn = ! theMethod.getReturnType().getName().equals("void") && 
                (theMethod.getReturnType().isPrimitive() || 
                        //theMethod.getReturnType().isArray() ||
                    theMethod.getReturnType().getName().equals("java.lang.String"));
            return startsWithGet && notHasParameters && hasReturn;
        }//end isGetMethod

        /**Determina si el metodo es set*/
        private boolean isSetMethod(Method theMethod)
        {
            boolean startsWithSet = theMethod.getName().startsWith("set") && theMethod.getName().length() > 3;
            boolean hasOnlyOneParameterPrimitiveOrString = theMethod.getParameterTypes().length==1 &&
                (theMethod.getParameterTypes()[0].isPrimitive() || 
                        //theMethod.getParameterTypes()[0].isArray() || 
                    theMethod.getParameterTypes()[0].getName().equals("java.lang.String"));
            boolean noReturn = theMethod.getReturnType().getName().equals("void");
            return startsWithSet && hasOnlyOneParameterPrimitiveOrString  && noReturn;
        }//end isSetMethod

        /**Regresa un arreglo String con los nombres de las propiedades correspondientes a los metodos set*/
        public String [] getPropertiesWithGet()
        {
            return getProperties(theGetMethods);
        }//end getPropertiesWithGet

        /**Regresa un arreglo String con los nombres de las propiedades correspondientes a los metodos get*/
        public String [] getPropertiesWithSet()
        {
            return getProperties(theSetMethods);
        }//end getPropertiesWithSet

        /**Factorizando codigo*/
        private String [] getProperties(Method [] methods)
        {
            ArrayList properties = new ArrayList();
            for(int i=0; i<methods.length;i++)
                properties.add(methods[i].getName().substring(3));
            return (String []) properties.toArray();        
        }//end getProperties

        /**Regresa un arreglo con las propiedades declaradas que no tienen metodo get*/
        public String [] getPropertiesWithoutGet() throws Exception
        {
            Field [] fields = theClass.getDeclaredFields();
            StringBuffer properties = new StringBuffer();

            for(int i=0; i<fields.length;i++)
            {
                String propertie = fields[i].getName().substring(0,1).toUpperCase() + fields[i].getName().substring(1);
                if(getMethodGet(propertie) == null)
                    properties.append(fields[i].getName()+",");
            }//end for fields

            if(properties.length() > 0)return properties.substring(0, properties.length()-1).split(",");
            else return new String[0];
        }//end getPropertiesWithoutGet

        /**Regresa un arreglo con las propiedades declaradas que no tienen metodo get*/
        public String [] getPropertiesWithoutSet() throws Exception
        {
            Field [] fields = theClass.getDeclaredFields();
            StringBuffer properties = new StringBuffer();

            for(int i=0; i<fields.length;i++)
            {
                String propertie = fields[i].getName().substring(0,1).toUpperCase() + fields[i].getName().substring(1);
                if(getMethodSet(propertie) == null)
                    properties.append(fields[i].getName()+",");
            }//end for fields

            if(properties.length() > 0)return properties.substring(0, properties.length()-1).split(",");
            else return new String[0];
        }//end getPropertiesWithoutSet    

        /**Obtiene el Method cuyo nombre esta prefijado con "set" y el sufijo correspondiente a propertie*/
        public Method getMethodSet(String propertie) throws Exception
        {
            Method method = null;
            propertie = propertie.substring(0,1).toUpperCase() + ( propertie.length() > 1 ? propertie.substring(1) : "" );
            for(int i=0; i<theSetMethods.length;i++){
                if(propertie.equals(theSetMethods[i].getName().substring(3))){
                    method = theClass.getMethod("set"+propertie, theSetMethods[i].getParameterTypes());
                    break;
                }//end if
            }//end for
            return method;
        }//end

        /**Obtiene el Method cuyo nombre esta prefijado con "get" y el sufijo correspondiente a propertie*/
        public Method getMethodGet(String propertie) throws Exception
        {
            Method method = null;      
            propertie = propertie.substring(0,1).toUpperCase() + ( propertie.length() > 1 ? propertie.substring(1) : "" );
            for(int i=0; i<theGetMethods.length;i++){
                if(propertie.equals(theGetMethods[i].getName().substring(3))){
                    method = theClass.getMethod("get"+propertie, new Class[]{});
                    break;
                }//end if
            }//end for
            return method;
        }//end    

        /**Metodo de acceso a la propiedad getPropertiesWithGetSet*/
        public String [] getPropertiesWithGetSet(){
            String propertie = null;
            if(this.propertiesWithGetSet==null){
                ArrayList set = new ArrayList();
                ArrayList getSet = new ArrayList();

                for(int i=0; i<this.theSetMethods.length;i++)
                    set.add(this.theSetMethods[i].getName().substring(3));

                for(int i=0; i<this.theGetMethods.length;i++)
                    if(set.contains(this.theGetMethods[i].getName().substring(3)))
                        getSet.add(this.theGetMethods[i].getName().substring(3));

                this.propertiesWithGetSet = new String[getSet.size()];
                for(int i=0; i<getSet.size();i++){
                    propertie = (String)getSet.get(i);
                    propertie = propertie.substring(0,1).toLowerCase() + ( propertie.length() > 1 ? propertie.substring(1) : "" );
                    this.propertiesWithGetSet[i] = propertie;
                }
            }//end if
            return this.propertiesWithGetSet;
        }//end method getPropertiesWithGetSet

        /**Metodo de acceso a la propiedad theObject*/
        public Object getTheObject() throws Exception{
            Object obj=null;
            if(theObjectIsArray()) {
                obj = (Object)Array.get(theObject, theObjectPosition);
            }
            else obj = this.theObject;
            return obj;
        }//end method getTheObject

        /**Metodo de modificacion a la propiedad theObject*/
        public void setTheObject(Object theObject) throws Exception
        {
            this.theObject = theObject;
            this.setTheClass();
            this.introspectForGetterSetter();
        }//end method getTheObject

        private void setTheClass() throws Exception
        {
            Object obj=null;
            if(theObjectIsArray()){
                this.calculateLengthArray();
                obj = (Object)Array.get(theObject, 0);
            }//end is array
            else{
                obj = this.theObject;
            }//end not is array
            this.theClass = obj.getClass();
        }//end setTheClass

        private void setTheClass(Class theClass) throws Exception
        {
            this.theClass = theClass;
            this.introspectForGetterSetter();
        }//end setTheClass

        /***/
        public boolean theObjectIsArray()
        {
            return  this.theObject.toString().indexOf("[")!=-1;
        }//end isArray

        public int getTheObjectPosition(){return this.theObjectPosition;}

        public void setTheObjectPosition(int i){this.theObjectPosition=i;}

        public void firstObject(){this.theObjectPosition=0;}

        public void lastObject(){this.theObjectPosition=this.totalObjects-1;}

        public void nextObject(){this.theObjectPosition = (this.theObjectPosition+1)%this.totalObjects;}

        public void previousObject(){this.theObjectPosition = (this.theObjectPosition -1+this.totalObjects)%this.totalObjects;}

        public int getTotalObjects(){return this.totalObjects;}

        private void calculateLengthArray()
        {
            boolean error = false;

            try{
                this.totalObjects = Array.getLength(theObject);
                this.totalObjects++;
            }
            catch(Exception e){error = true;}  

            /*
            //En Java 1.0 en 1999 no funcionaba el método Array.getLength Nota de MJVC 2016/09/10
            this.totalObjects = 0;     
            while(! error){
            try{
            Array.get(theObject, this.totalObjects);
            this.totalObjects++;
            }catch(Exception e){error = true;}        
            }//end while
             */

        }//end calculateLengthArray

        /**Metodo de acceso a la propiedad theClass*/
        public Class getTheClass(){
            return this.theClass;
        }//end method getTheClass

        /**Metodo de acceso a la propiedad theGetMethods*/
        public Method[] getTheGetMethods(){
            return this.theGetMethods;
        }//end method getTheGetMethods

        /**Metodo de acceso a la propiedad theSetMethods*/
        public Method[] getTheSetMethods(){
            return this.theSetMethods;
        }//end method getTheSetMethods

        public String invokeGetter(String propertie)
        {
            String result;
            Object obj=null;
            try{
                Method method = this.getMethodGet(propertie);
                obj = getTheObject();
                result = method.invoke(obj, new Object[]{}).toString();
            }//end try
            catch(Exception e){
                result = e.getMessage();
            }
            return result;
        }//end invokeGetter

        public boolean invokeSetter(String propertie, String textValue)
        {
            boolean rta = true;
            Object obj=null;
            try{
                Method method = this.getMethodSet(propertie);
                obj = getTheObject();
                String typeParameter = method.getParameterTypes()[0].getSimpleName();
                int type = primitives.indexOf(typeParameter)/10;
                type = type < 0 ? wrappers.indexOf(typeParameter)/10 : type;
                Object value=null;        
                switch(type){
                    case 0: value = new Character(textValue.charAt(0));break;
                    case 1: value = Byte.valueOf(textValue);break;
                    case 2: value = Short.valueOf(textValue);break;
                    case 3: value = Integer.valueOf(textValue);break;
                    case 4: value = Long.valueOf(textValue);break;
                    case 5: value = Float.valueOf(textValue);break;
                    case 6: value = Double.valueOf(textValue);break;
                    case 7: value = textValue;break;
                    case 8: value = new Boolean(textValue);break;
                    default: rta = false;
                }//end switch
                method.invoke(obj, new Object[]{value});
            }//end try
            catch(Exception e){
                rta = e.getMessage().indexOf("result==null")!=-1;
            }//fin catch
            return rta;
        }//end invokeSetter    

    }//fin class GetterSetterIntrospector

}//fin class DiscoDuro
