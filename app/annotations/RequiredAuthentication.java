package annotations;

import play.Logger;
import play.libs.F.Promise;
import play.mvc.Action.Simple;
import play.mvc.Http.Context;
import play.mvc.Result;
import utils.RateLimitAgent;

public class RequiredAuthentication extends Simple {
	@Override
	public Promise<Result> call(Context ctx) throws Throwable {
		String accessToken = ctx.request().getHeader("x-access-token");
		if(accessToken!=null){
			//Check RateLimit
			Logger.debug("Checking for token RateLimit. token:"+accessToken);
			if(!RateLimitAgent.isRateOverLimit(accessToken)){
				return Promise.<Result>pure(badRequest("Over RateLimit. Account temporaly suspended"));
			}
		}else{
			return Promise.<Result>pure(badRequest("Authentication need"));
		}

        return delegate.call(ctx);
	}
	
}
