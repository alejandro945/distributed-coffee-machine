package service;

import java.lang.reflect.Method;

import com.zeroc.Ice.Current;
import servicios.ProxyService;
import servicios.RecetaServicePrx;

public class ProxyServices implements ProxyService {

    RecetaServicePrx recetaServicePrx;
    CacheService cacheService;

    public ProxyServices(RecetaServicePrx recetaServicePrx) {
        this.recetaServicePrx = recetaServicePrx;
        this.cacheService = CacheService.getInstance();
    }

    private String[] consultar(String key, String methodName) {
        if (cacheService.getElementFromCache(key) != null) {
            return cacheService.getElementFromCache(key);
        }
        try {
            Method method = recetaServicePrx.getClass().getMethod(methodName);
            String[] result = (String[]) method.invoke(recetaServicePrx);
            cacheService.putElementInCache(key, result);
            return cacheService.getElementFromCache(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String[] consultarIngredientesProxy(Current current) {
        return consultar("ingredientes", "consultarIngredientes");
    }

    @Override
    public String[] consultarRecetasProxy(Current current) {
        return consultar("recetas", "consultarRecetas");
    }

    @Override
    public String[] consultarProductosProxy(Current current) {
        return consultar("productos", "consultarProductos");
    }

}
